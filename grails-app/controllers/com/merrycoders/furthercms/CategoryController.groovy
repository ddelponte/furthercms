package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class CategoryController {
    def utilityService
    def categoryService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create() {
        [categoryInstance: new Category(params)]
    }

    def save() {
        def categoryInstance = new Category(params)
        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
        redirect(action: "show", id: categoryInstance.id)
    }

    def update(CategoryUpdateCommand cmd) {
        if (!cmd.page.validate() || !cmd.category.validate()) {
            if (request.xhr) {
                renderAjaxResponse(cmd)
            } else {
                render ""
                return
            }
        }

        def categoryInstance = cmd.category
        categoryInstance.page = cmd.page

        try {
            categoryService.save(cmd.category, true)
        } catch (Exception ex) {
            log.error("Error save category $categoryInstance with page ${categoryInstance.page}")
        }

        if (request.xhr) {
            renderAjaxResponse(cmd)
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
            redirect(controller: "admin", action: "edit", id: categoryInstance.id)
        }
    }

    private renderAjaxResponse(CategoryUpdateCommand cmd) {
        AjaxPostResponse ajaxPostResponse = utilityService.preparePostResponse([cmd.page, cmd.category])
        def errors = ajaxPostResponse?.errors

        errors.each { k, v ->
            errors[k] = ui.message([type: "error"], v)
        }

        response.status = 200
        render ajaxPostResponse as JSON
        return
    }

    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "show", id: id)
        }
    }

}

class CategoryUpdateCommand {
    Page page = new Page()
    Category category = new Category()

    static constraints = {
        page nullable: false
        category nullable: false
    }
}
