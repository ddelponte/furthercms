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
        metaDescription nullable: true
        metaKeywords nullable: true
        linkText nullable: true
    }

    static mapping = {
        cache true
        pageType fetch: 'join'
    }

    def beforeDelete() {
        PageData.withNewSession { pageDatas*.delete() }
    }

    def getPageDatas() {
        PageData.findAllByPage(this, [sort: "name"])
    }

    String toString() {
        title
    }
}
