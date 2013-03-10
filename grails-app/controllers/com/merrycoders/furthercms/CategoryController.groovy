package com.merrycoders.furthercms

import org.springframework.dao.DataIntegrityViolationException

class CategoryController {
    def categoryService
    def moduleService

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

    def update() {
        Long categoryId = params.long("category.id")
        Long categoryVersion = params.long("category.version")
        def categoryInstance = Category.get(categoryId)

        Long pageId = params.long("page.id")
        Long pageVersion = params.long("page.version")
        def pageInstance = Page.get(pageId)
        pageInstance.properties = params.page

        //moduleService.convertFormInputToModules(pageInstance, params.modules)

        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), categoryId])
            redirect(action: "list")
            return
        }

        if (categoryVersion != null) {
            if (categoryInstance.version > categoryVersion) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'category.label', default: 'Category')] as Object[],
                        "Another user has updated this Category while you were editing")
                render(view: "edit", model: [categoryInstance: categoryInstance])
                return
            }
        }

        categoryInstance.properties = params.category

        if (!categoryInstance.save(flush: true)) {
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        if (request.xhr) {
            response.status = 200
            render "test"
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
            redirect(controller: "admin", action: "edit", id: categoryInstance.id)
        }
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

    def test() {
        println params
    }
}
