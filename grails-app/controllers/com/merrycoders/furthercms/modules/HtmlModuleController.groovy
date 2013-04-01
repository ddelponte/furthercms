package com.merrycoders.furthercms.modules

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HtmlModuleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def utilityService

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

        // Ignore version check for now
//        if (version != null) {
//            if (htmlModuleInstance.version > version) {
//                htmlModuleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
//                        [message(code: 'htmlModule.label', default: 'HtmlModule')] as Object[],
//                        "Another user has updated this HtmlModule while you were editing")
//                if (request.xhr) {
//                    response.status = 200
//                    render "test"
//                } else {
//                    render(view: "edit", model: [htmlModuleInstance: htmlModuleInstance])
//                }
//                return
//            }
//        }

        htmlModuleInstance.properties = params
        def html = params.find { k, v -> k.startsWith("html_") }?.value ?: ""
        htmlModuleInstance.html = html

        if (!htmlModuleInstance.save(flush: true)) {
            if (request.xhr) {
                renderAjaxResponse(htmlModuleInstance)
                return
            } else {
                render(view: "edit", model: [htmlModuleInstance: htmlModuleInstance])
            }
            return
        }

        if (request.xhr) {
            renderAjaxResponse(htmlModuleInstance)
            return
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'htmlModule.label', default: 'HtmlModule'), htmlModuleInstance.id])
            redirect(action: "edit", id: htmlModuleInstance.id)
        }
    }

    private renderAjaxResponse(HtmlModule htmlModuleInstance) {
        AjaxPostResponse ajaxPostResponse = utilityService.preparePostResponse([htmlModuleInstance])
        response.status = 200
        render ajaxPostResponse as JSON
        return
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

    def renderEditor(String name) {
        log.info params
        render(template: "/modules/html/ckeditor", model: [name: name])
    }
}
