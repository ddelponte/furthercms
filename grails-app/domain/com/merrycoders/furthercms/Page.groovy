package com.merrycoders.furthercms

class Page {
    String title
    String metaDescription
    String metaKeywords
    String linkText
    Long dataKey // The key used to pull additional data.  Often associated with PageType
    PageType pageType

    static constraints = {
        dataKey(nullable: true)
        metaDescription(nullable: true)
        metaKeywords(nullable: true)
    }

    String toString() {
        title
    }
}
