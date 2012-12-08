package com.merrycoders.furthercms

import org.codehaus.groovy.grails.validation.routines.UrlValidator

/**
 * Represents the node of a site's tree, often corresponding to a specific URL and page
 */
class Category {
    String name = ""
    String description
    Category parent
    String urlKey
    Page page
    Boolean isPublished = false // If true, it's viewable by the public
    Boolean isInMenu = false // If true, it's displayed in the main menu
    Date dateCreated
    Date lastUpdated

    static constraints = {
        description nullable: true
        parent nullable: true
        parent nullable: true
        urlKey(unique: true, validator: {
            UrlValidator v = new UrlValidator()
            return v.isValid("http://www.google.com/" + it)
        })
        page nullable: true
    }

    static mapping = {
        cache true
        urlKey column: 'url_key', index: 'UrlKey_Idx'
    }

    /**
     *
     * @return List of immediate children
     */
    List<Category> getChildren() {
        if (this?.id) Category.findAllByParent(this, [sort: "name"])
        else return []
    }

    /**
     *
     * @return List of all children at all levels
     */
    List<Category> getDescendants() {
        def list = []

        def children = getChildren()
        list.addAll(children)

        children.each { child ->
            list.addAll(child.getDescendants())
        }
        return list
    }
}
