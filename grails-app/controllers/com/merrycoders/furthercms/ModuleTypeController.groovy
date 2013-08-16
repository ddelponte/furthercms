package com.merrycoders.furthercms

import org.springframework.dao.DataIntegrityViolationException

class ModuleTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def moduleService

    /**
     * Render a ModuleType's edit view
     * @param id ModuleType id
     * @return html which renders the ModuleType's edit view
     */
    def renderModuleEdit(Long id) {
        if (!id) {
            render ""
            return
        }

        def moduleData = getModuleEditTagAndCreateModule(id)
        def moduleEditTag = moduleData.moduleEditTag

        render moduleEditTag
    }

    /**
     * Render a ModuleType's edit view wrapped in an li and a section element
     * @param id The ModuleType id
     */
    def renderNewModuleEditListItem(Long id) {
        if (!id) {
            render ""
            return
        }

        def moduleData = getModuleEditTagAndCreateModule(id)
        def moduleEditTag = moduleData.moduleEditTag
        def module = moduleData.module
        def sectionElement = "<section class=\"module\" data-module-name=\"${module}\" data-module-id=\"${module?.id}\">"
        def errorElement = "<div class=\"errors\" style=\"display: none;\"></div>"

        render "<li>${sectionElement}${errorElement}${moduleEditTag}</section></li>"

    }

    /**
     * Creates a new module for the specified ModuleType and page
     * @param id The ModuleType id
     * @return a Map containing the moduleEditTag markup and module instance associated with the tag
     */
    private getModuleEditTagAndCreateModule(Long id) {
        def moduleTypeInstance = ModuleType.get(id)
        def pageInstance = Page.get(params.long("page.id"))
        def module = moduleService.create([moduleType: moduleTypeInstance, page: pageInstance])

        return [moduleEditTag: fc.renderModuleEdit([module: module], ""), module: module]
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [moduleTypeInstanceList: ModuleType.list(params), moduleTypeInstanceTotal: ModuleType.count()]
    }

    def create() {
        [moduleTypeInstance: new ModuleType(params)]
    }

    def save() {
        def moduleTypeInstance = new ModuleType(params)
        if (!moduleTypeInstance.save(flush: true)) {
            render(view: "create", model: [moduleTypeInstance: moduleTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), moduleTypeInstance.id])
        redirect(action: "show", id: moduleTypeInstance.id)
    }

    def show(Long id) {
        def moduleTypeInstance = ModuleType.get(id)
        if (!moduleTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "list")
            return
        }

        [moduleTypeInstance: moduleTypeInstance]
    }

    def edit(Long id) {
        def moduleTypeInstance = ModuleType.get(id)
        if (!moduleTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "list")
            return
        }

        [moduleTypeInstance: moduleTypeInstance]
    }

    def update(Long id, Long version) {
        def moduleTypeInstance = ModuleType.get(id)
        if (!moduleTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (moduleTypeInstance.version > version) {
                moduleTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'moduleType.label', default: 'ModuleType')] as Object[],
                        "Another user has updated this ModuleType while you were editing")
                render(view: "edit", model: [moduleTypeInstance: moduleTypeInstance])
                return
            }
        }

        moduleTypeInstance.properties = params

        if (!moduleTypeInstance.save(flush: true)) {
            render(view: "edit", model: [moduleTypeInstance: moduleTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), moduleTypeInstance.id])
        redirect(action: "show", id: moduleTypeInstance.id)
    }

    def delete(Long id) {
        def moduleTypeInstance = ModuleType.get(id)
        if (!moduleTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "list")
            return
        }

        try {
            moduleTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'moduleType.label', default: 'ModuleType'), id])
            redirect(action: "show", id: id)
        }
    }
}
