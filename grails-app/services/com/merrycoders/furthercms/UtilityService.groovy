package com.merrycoders.furthercms

/**
 * A collection of useful methods
 */
class UtilityService {
    static transactional = false

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
}
