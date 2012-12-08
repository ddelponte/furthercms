package com.merrycoders.furthercms

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
}
