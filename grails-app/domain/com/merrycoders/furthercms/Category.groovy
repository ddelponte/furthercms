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
    Boolean isInSecondaryNavigation = false
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
        urlKey index: 'UrlKey_Idx'
    }

    /**
     *
     * @return List containing this Category and it's ancestors.  For example, [GrandParentCategory, ParentCategory, ThisCategory
     */
    List<Category> getAncestry() {
        def categoryInstanceList

        if (parent) categoryInstanceList = parent.getAncestry()
        else categoryInstanceList = new LinkedList<Category>()

        categoryInstanceList << this
        return categoryInstanceList
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
     * @return List of this category and all of its children at all levels
     */
    List<Category> getDescendants() {
        def categoryInstanceList = []

        def children = getChildren()
        categoryInstanceList.addAll(children)

        children.each { child ->
            categoryInstanceList.addAll(child.getDescendants())
        }
        return categoryInstanceList
    }

    String toString() {
        name
    }
}
