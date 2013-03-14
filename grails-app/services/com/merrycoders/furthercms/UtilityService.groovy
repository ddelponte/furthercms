package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import org.apache.commons.lang.WordUtils

/**
 * A collection of useful methods
 */
class UtilityService {
    static transactional = false
    def grailsApplication

    /**
     *  A slug is the part of a URL which identifies a page using human-readable keywords [1][2], for example "Slug_(web_publishing)", rather than an opaque
     *  identifier such as the ID number of the content within the database (e.g. "4859604"). Slugs are used to construct clean URLs (often for permalinks)
     *  that are easy to type, descriptive, and easy to remember.
     *  For example, in this URL:  http://example.org/2011/introduction-to-blogging
     *  the slug is "introduction-to-blogging".
     * @param pathFragment The String to be converted into a slug
     * @return The passed in String  as a slug String.  For example, "Further CMS" is returned as "further-cms"
     */
    def toSlug(pathFragment) {
        def whiteSpacePattern = ~/[^\w-]+/
        def nonAlphaNumericPattern = ~/[^\w-]+/
        def htmlCodePattern = ~/&[a-zA-Z]*;/

        def slug = (pathFragment =~ htmlCodePattern).replaceAll("")
        slug = ("${slug.toLowerCase()}" =~ whiteSpacePattern).replaceAll("-")
        slug = (slug =~ nonAlphaNumericPattern).replaceAll("")
        return slug
    }

    /**
     * Turns an instance of a domain class into an object that is friendly to JavaScript on the client
     * @param domainInstance
     * @return
     */
    def AjaxPostResponse preparePostResponse(List domainInstances) {
        def g = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
        def ajaxPostResponse = new AjaxPostResponse(domainObjects: domainInstances)

        domainInstances.each { domainInstance ->

            if (domainInstance.hasErrors()) {
                def simpleClassName = WordUtils.uncapitalize(domainInstance.class.simpleName)

                g.eachError(bean: domainInstance) {
                    ajaxPostResponse.errors."${simpleClassName}.${it.field}" = g.message(error: it)
                }

                ajaxPostResponse.success = false
                ajaxPostResponse.message = "There was an error"

            }

        }
        return ajaxPostResponse
    }
}
