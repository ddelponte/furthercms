package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class CategoryController {
    def utilityService
    def categoryService
    def moduleService

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

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
                renderAjaxResponse([cmd.page, cmd.category])
                return
            } else {
                render ""
                return
            }
        }

        def categoryInstance = cmd.category
        categoryInstance.page = cmd.page

        try {
            categoryService.save(cmd.category, true)
            def modulesToDeleteMap = JSON.parse(cmd.modulesToDelete)
            def moduleIdsToDelete = modulesToDeleteMap.keySet()*.toLong()
            moduleService.delete(moduleIdsToDelete)
        } catch (Exception ex) {
            log.error("Error save category $categoryInstance with page ${categoryInstance.page}")
        }

        if (request.xhr) {
            renderAjaxResponse([cmd.page, cmd.category])
            return
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
            redirect(controller: "admin", action: "edit", id: categoryInstance.id)
        }
    }

    private renderAjaxResponse(List domainObjects) {
        AjaxPostResponse ajaxPostResponse = utilityService.preparePostResponse(domainObjects)
        response.status = 200
        render ajaxPostResponse as JSON
        return
    }

    /**
     * Delete the Category instance and associated Page instance
     * @param id Category id
     * @return redirect to the home page editor
     */
    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            displayFlashMessage([text: 'default.not.found.message', type: "warning", args: [message(code: 'category.label', default: 'Category'), id]])
            redirect(controller: "admin", action: "index")
            return
        }

        def url = categoryInstance.urlKey

        try {
            categoryService.delete(categoryInstance)
            displayFlashMessage([text: "category.delete.success", args: [url]])
            redirect(controller: "admin", action: "index")
        }
        catch (DataIntegrityViolationException e) {
            log.error("Unable to delete category with id of ${id}", e)
            displayFlashMessage([text: 'default.not.deleted.message', type: "error", args: [message(code: 'category.label', default: 'Category'), id]])
            redirect(controller: "admin", action: "edit", id: categoryInstance?.id)
        }
    }

    /**
     * Assigns a new parent Category associated with the parentId to the Category associated with the id.  All children of the Category have their urlKey
     * updated to reflect the change
     * @param id
     * @param parentId
     * @return an AjaxResponseObject
     */
    def move(Long id, Long parentId) {
        def category = Category.get(id)
        def parentCategory = Category.get(parentId)
        categoryService.move(category, parentCategory)
        renderAjaxResponse([category, parentCategory])
        return
    }

}

class CategoryUpdateCommand {
    Page page = new Page()
    Category category = new Category()
    String modulesToDelete

    static constraints = {
        page nullable: false
        category nullable: false
    }
}
