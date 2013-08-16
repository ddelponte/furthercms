package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import com.merrycoders.furthercms.exceptions.InvalidCategoryMoveException
import grails.converters.JSON
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

class CategoryController {
    def utilityService
    def categoryService
    def moduleService

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

    /**
     * Create and save a new category instance.  The new Category instance will be assigned the parent Category associated with the id.
     * @param id parent Category id
     * @return redirects to the edit page of the newly created Category or renders an Ajax response for an ajax request
     */
    def createAndSave(Long id) {
        if (id == null) id = Category.findByUrlKey("").id
        def parent = Category.get(id)
        def title = params.title ?: "New Page"
        def pageType = params?.pageTypeKey ? PageType.findByPageTypeKey(params?.pageTypeKey) : PageType.findByPageTypeKey("HTML")
        pageType = pageType ?: PageType.findByPageTypeKey("HTML")
        def themeLayout = parent?.page?.themeLayout ?: "sidebar"
        def category

        try {
            category = categoryService.createAndSave([
                    page: [title: title, pageType: pageType, themeLayout: themeLayout],
                    category: [parent: parent],
                    flush: true])

        } catch (ValidationException ex) {
            if (request.xhr) {
                AjaxPostResponse ajaxPostResponse = utilityService.preparePostResponse([category])
                ajaxPostResponse.message = ex.errors.allErrors.collect { g.message(error: it) }?.join(". ")
                ajaxPostResponse.success = false
                renderAjaxPostResponseObject(ajaxPostResponse)
                return
            } else {
                redirect(action: "edit", id: parent.id)
                return
            }
        }

        if (request.xhr) {

            AjaxPostResponse ajaxPostResponse = utilityService.preparePostResponse([category])
            if (ajaxPostResponse.success) {
                ajaxPostResponse.message = category?.id
            }

            renderAjaxPostResponseObject(ajaxPostResponse)
            return

        } else {

            redirect(action: "edit", id: category.id)
            return
        }

    }

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
            def modulesToDeleteMap = JSON.parse(cmd.modulesToDelete ?: "{}")
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
        renderAjaxPostResponseObject(ajaxPostResponse)
    }

    private renderAjaxPostResponseObject(AjaxPostResponse ajaxPostResponseInstance) {
        response.status = 200
        render ajaxPostResponseInstance as JSON
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
     * @param position JSON representation of the moved node and its siblings, properly ordered in the form {position: category.id}.  For example: "{0:1, 1:2, 2:88}"
     * @return an AjaxResponseObject
     */
    def move(Long id, Long parentId) {
        String positions = params.positions ?: "{}"
        def category = Category.get(id)
        def parentCategory = Category.get(parentId)

        try {

            categoryService.move(category, parentCategory, positions)

        } catch (ValidationException e) {
            category = Category.read(id)
            category.errors = e.errors
        } catch (InvalidCategoryMoveException e) {
            def ajaxPostResponse = new AjaxPostResponse(success: false, message: e.message, errors: g.message(code: "invalid.category.move"))
            renderAjaxPostResponseObject(ajaxPostResponse)
            return
        }

        renderAjaxResponse([category, parentCategory])
        return
    }

    /**
     * Render the urlKey for the category with the specified id
     * @param id
     * @return
     */
    def urlKey(Long id) {
        if (!id) {
            render ""
            return
        } else {
            def category = Category.get(id)
            render category?.urlKey
            return
        }
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
