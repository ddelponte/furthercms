package com.merrycoders.furthercms

import org.springframework.dao.DataIntegrityViolationException

class PageTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        def model = [contentTemplatePath: "/admin/primaryAdminMenuItems/pageTypes/list", pageTypeInstanceList: PageType.list(params), pageTypeInstanceTotal: PageType.count(), params: params]
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
        redirect(action: "show", id: pageTypeInstance.id)
    }

    def show(Long id) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
            redirect(action: "list")
            return
        }

        [pageTypeInstance: pageTypeInstance]
    }

    def edit(Long id) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
            redirect(action: "list")
            return
        }

        [pageTypeInstance: pageTypeInstance]
    }

    def update(Long id, Long version) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pageType.label', default: 'PageType'), pageTypeInstance.id])
        redirect(action: "show", id: pageTypeInstance.id)
    }

    def delete(Long id) {
        def pageTypeInstance = PageType.get(id)
        if (!pageTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
            redirect(action: "list")
            return
        }

        try {
            pageTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pageType.label', default: 'PageType'), id])
            redirect(action: "show", id: id)
        }
    }

    def listModuleTypes(Long id) {
        def pageInstance = Page.get(params.long("page.id"))
        def pageTypeInstance = PageType.get(id)
        def moduleTypes = PageTypeModuleType.findAllByPageType(pageTypeInstance)?.moduleType?.sort { it?.name }
        render(template: "/admin/moduleTypes/list", model: [pageInstance: pageInstance, moduleTypes: moduleTypes])
    }
}
