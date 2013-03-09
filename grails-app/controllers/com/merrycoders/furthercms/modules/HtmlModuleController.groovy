package com.merrycoders.furthercms.modules

import org.springframework.dao.DataIntegrityViolationException

class HtmlModuleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [htmlModuleInstanceList: HtmlModule.list(params), htmlModuleInstanceTotal: HtmlModule.count()]
    }

    def create() {
        [htmlModuleInstance: new HtmlModule(params)]
    }

    def save() {
        def htmlModuleInstance = new HtmlModule(params)
        if (!htmlModuleInstance.save(flush: true)) {
            render(view: "create", model: [htmlModuleInstance: htmlModuleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), htmlModuleInstance.id])
        redirect(action: "show", id: htmlModuleInstance.id)
    }

    def show(Long id) {
        def htmlModuleInstance = HtmlModule.get(id)
        if (!htmlModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "list")
            return
        }

        [htmlModuleInstance: htmlModuleInstance]
    }

    def edit(Long id) {
        def htmlModuleInstance = HtmlModule.get(id)
        if (!htmlModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "list")
            return
        }

        [htmlModuleInstance: htmlModuleInstance]
    }

    def update(Long id, Long version) {
        def htmlModuleInstance = HtmlModule.get(id)
        if (!htmlModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (htmlModuleInstance.version > version) {
                htmlModuleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'htmlModule.label', default: 'HtmlModule')] as Object[],
                        "Another user has updated this HtmlModule while you were editing")
                render(view: "edit", model: [htmlModuleInstance: htmlModuleInstance])
                return
            }
        }

        htmlModuleInstance.properties = params

        if (!htmlModuleInstance.save(flush: true)) {
            render(view: "edit", model: [htmlModuleInstance: htmlModuleInstance])
            return
        }
        htmlModuleInstance.save(flush: true)

        if (request.xhr) {
            response.status = 200
            render "test"
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), htmlModuleInstance.id])
            redirect(action: "edit", id: htmlModuleInstance.id)
        }
    }

    def delete(Long id) {
        def htmlModuleInstance = HtmlModule.get(id)
        if (!htmlModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "list")
            return
        }

        try {
            htmlModuleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), id])
            redirect(action: "show", id: id)
        }
    }
}
