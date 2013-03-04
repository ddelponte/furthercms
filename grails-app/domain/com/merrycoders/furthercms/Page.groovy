package com.merrycoders.furthercms

class Page {
    String title
    String metaDescription
    String metaKeywords
    String linkText
    PageType pageType
    Boolean isPublished = false // If true, category is viewable by the public
    Boolean isInMenu = true // If true, category is displayed in the main menu
    Boolean isHidden = false // If false, category is not automatically displayed in generated listings
    String themeLayout
    Date dateCreated
    Date lastUpdated

    static constraints = {
        metaDescription nullable: true, maxSize: 10000
        metaKeywords nullable: true, maxSize: 10000
        linkText nullable: true, maxSize: 10000
    }

    static mapping = {
        cache true
        pageType fetch: 'join'
        modules fetch: 'join'
    }

    def beforeDelete() {
        Module.withNewSession { modules*.delete() }
    }

    def getModules() {
        Module.findAllByPage(this, [sort: "displayOrder", order: "asc"])
    }

    String toString() {
        title
    }
}
