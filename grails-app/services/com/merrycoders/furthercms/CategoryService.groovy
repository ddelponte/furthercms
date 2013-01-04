package com.merrycoders.furthercms

import grails.validation.ValidationException
import org.apache.commons.lang.StringUtils

class CategoryService {
    static transactional = false

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
     * @return The saved Category or PrimaryCategory instance
     */
    def save(category, flush = false) {
        if (!category) return null

        if (!category?.displayOrder) {
            def maxDisplayOrder = category.siblings?.displayOrder ? category.siblings?.displayOrder?.max() + 1 : 0
            category?.displayOrder = maxDisplayOrder
        }

        if (!category.validate()) {
            throw new ValidationException("Category is not valid", category.errors)
        }

        category.save(flush: flush)
    }
}
