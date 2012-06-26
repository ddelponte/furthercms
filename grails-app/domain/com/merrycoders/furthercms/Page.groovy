package com.merrycoders.furthercms

class Page {
    String title
    String metaDescription
    String metaKeywords
    String linkText
    Long dataKey // The key used to pull additional data.  Often associated with PageType
    PageType pageType
    Boolean isPublished = false // If true, it's viewable by the public
    Boolean isInMenu = true // If true, it's displayed in the main menu
    Boolean isHidden = false // If false, category is not automatically displayed in generated listings

    static constraints = {
        dataKey(nullable: true)
        metaDescription(nullable: true)
        metaKeywords(nullable: true)
    }

    static mapping = {
        cache true
    }

    String toString() {
        title
    }
}
