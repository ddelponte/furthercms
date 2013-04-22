package com.merrycoders.furthercms

import grails.validation.ValidationException
import org.apache.commons.lang.StringUtils

class CategoryService {
    def pageService
    def primaryCategoryService
    def utilityService

    /**
     * Returns the category instance associated with the urlKey
     * @param urlKey
     * @param fetchPage If true, also returns the Category object's associated Page object.  Defaults to true
     * @return Category instance if it exists, else null
     */
    Category findByUrlKey(String urlKey, Boolean fetchPage = true) {
        if (urlKey == null) return null
        def queryMap = [:]
        if (fetchPage) queryMap = [fetch: [page: "join"]]
        def sanitizedPath = StringUtils.stripEnd(urlKey, "/")
        return Category.findByUrlKey(sanitizedPath, queryMap)

    }

    /**
     * Saves both Category and PrimaryCategory instances and ensures their displayOrder is properly set
     * @param category
     * @param flush
     * @throws ValidationException
     * @return The saved Category or PrimaryCategory instance
     */
    Category save(Category category, Boolean flush = false) throws ValidationException {
        if (!category) return null

        pageService.save(category.page, true)

        if (!category?.displayOrder) {
            def maxDisplayOrder = category.siblings?.displayOrder ? category.siblings?.displayOrder?.max() + 1 : 0
            category?.displayOrder = maxDisplayOrder
        }

        if (category.page) {
            def parentUrlKey = category.parent?.urlKey ? "${category.parent?.urlKey}/" : ""
            category.urlKey = "${parentUrlKey}${utilityService.toSlug(category.page?.title)}"
        }

        if (!category.validate()) {
            throw new ValidationException("Category is not valid", category.errors)
        }

        category.save(flush: flush)
    }

    /**
     * Delete the Category instance as well as its associated PrimaryCategory, Page and Module instances
     * @param category
     */
    void delete(Category category) {
        if (category) {
            category.children.each { childCategory -> delete(childCategory) }

            def primaryCategories = PrimaryCategory.findAllByCategory(category)
            primaryCategoryService.delete(primaryCategories)

            def page = category.page
            category.page = null
            category.delete(flush: true)

            pageService.delete(page)


        }
    }

    /**
     * Assigns the parent to the category, updating the urlKeys of the category's children.
     * Users are not allowed to move a Category to one of it's children, thereby making the child its parent.
     * @param category
     * @param parent
     * @throws ValidationException
     * @return The category instance with the new parent
     */
    Category move(Category category, Category parent) throws ValidationException {
        if (!category || !parent || category?.descendants?.contains(parent) || category == parent) return null

        category.parent = parent
        save(category)

        for (child in category.children) {
            save(child)
        }

        return category
    }
}
