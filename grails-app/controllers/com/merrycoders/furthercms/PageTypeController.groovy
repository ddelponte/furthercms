package com.merrycoders.furthercms

import com.merrycoders.furthercms.exceptions.UndeletablePageTypeException
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class PageTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def pageTypeService
    def pageTypeModuleTypeService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        params.sort = params.sort ?: "name"
        params.order = params.order ?: "asc"

        def pageTypeListQuery = PageType.where {
            pageTypeKey != "root"
        }

        def model = [contentTemplatePath: "/admin/primaryAdminMenuItems/pageTypes/list", pageTypeInstanceList: pageTypeListQuery.list(params), pageTypeInstanceTotal: PageType.count(), params: params]
        request.chainModel = model
        forward controller: "admin", action: "index"
    }

    def edit(Long id) {
        def pageTypeInstance = PageType.get(id)

        if (!pageTypeInstance) {
            displayFlashMessage([text: 'default.not.found.message', type: "warning", args: [message(code: 'pageType.label', default: 'Page Type'), id]])
            redirect(action: "list")
            return
        }

        // TODO: Refactor active secondary menu items so they are associated with multiple actions
        params.action = "list"
        Map<PageTypeModuleTypeStatus, ModuleType> moduleTypeStatusMap = pageTypeService.findAllModuleTypesByPageType(pageTypeInstance)

        def model = [pageTypeInstance: pageTypeInstance, contentTemplatePath: "/admin/primaryAdminMenuItems/pageTypes/edit", moduleTypeStatusMap: moduleTypeStatusMap, params: params]
        request.chainModel = model

        forward controller: "admin", action: "index"
    }

    def create() {
        [pageTypeInstance: new PageType(params)]
    }

    def save() {
        def pageTypeInstance = new PageType(params)
        if (!pageTypeInstance.save(flush: true)) {
            render(view: "create", model: [pageTypeInstance: pageTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pageType.label', default: 'PageType'), pageTypeInstance.id])
        redirect(action: "list")
    }

    def update(Long id, Long version) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            displayFlashMessage([text: 'default.not.found.message', type: "warning", args: [message(code: 'pageType.label', default: 'Page Type'), id]])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pageTypeInstance.version > version) {
                pageTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pageType.label', default: 'PageType')] as Object[],
                        "Another user has updated this PageType while you were editing")
                render(view: "edit", model: [pageTypeInstance: pageTypeInstance])
                return
            }
        }

        pageTypeInstance.properties = params

        if (!pageTypeInstance.save(flush: true)) {
            render(view: "edit", model: [pageTypeInstance: pageTypeInstance])
            return
        }

        displayFlashMessage([text: 'default.updated.message', type: "info", args: [pageTypeInstance.name, ""]])
        redirect(action: "list")
    }

    /**
     * Saves a PageType instances ModuleType instances, status and order. All JSON Strings are in the for {moduleTypeId:PageTypeModuleTypeStatus, ...}* For example: {"1":"Active", "2":"Active", etc.
     * @param id PageType id
     * @param active JSON String of active and ordered ModuleType ids
     * @param available JSON String of available ModuleType ids
     * @param unavailable JSON String of unavailable ModuleType ids
     * @return
     */
    def updateModuleTypes(Long id) {
        def pageType = PageType.get(id)
        String active = params?.Active ?: "{}"
        String available = params?.Available ?: "{}"
        String unavailable = params?.Unavailable ?: "{}"

        def activeModuleTypeIds = JSON.parse(active)?.keySet() ?: []
        def availableModuleTypeIds = JSON.parse(available)?.keySet() ?: []
        def unavailableModuleTypeIds = JSON.parse(unavailable)?.keySet() ?: []

        pageTypeModuleTypeService.updateByModuleTypesById(pageType,
                [(PageTypeModuleTypeStatus.ACTIVE): activeModuleTypeIds*.toLong(),
                        (PageTypeModuleTypeStatus.AVAILABLE): availableModuleTypeIds*.toLong(),
                        (PageTypeModuleTypeStatus.UNAVAILABLE): unavailableModuleTypeIds*.toLong()])

        redirect(action: "edit", id: id)
    }

    def delete(Long id) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            displayFlashMessage([text: 'default.not.found.message', type: "warning", args: [message(code: 'pageType.label', default: 'PageType'), id]])
            redirect(action: "list")
            return
        }

        try {

            pageTypeService.delete(pageTypeInstance)

            displayFlashMessage([text: 'default.deleted.message', type: "info", args: [message(code: 'pageType.label', default: 'PageType'), id]])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            displayFlashMessage([text: 'default.not.deleted.message', type: "error", args: [pageTypeInstance?.name, ""]])
            redirect(action: "edit", id: id)
        } catch (UndeletablePageTypeException e) {
            displayFlashMessage([text: 'default.not.deleted.message', type: "error", args: [pageTypeInstance?.name, ""]])
            redirect(action: "edit", id: id)
        }
    }

    def listModuleTypes(Long id) {
        def pageInstance = Page.get(params.long("page.id"))
        def pageTypeInstance = PageType.get(id)
        def moduleTypes = PageTypeModuleType.findAllByPageType(pageTypeInstance)?.moduleType?.sort { it?.name }
        render(template: "/admin/moduleTypes/list", model: [pageInstance: pageInstance, moduleTypes: moduleTypes])
    }
}
