package com.merrycoders.furthercms

class PagePageTypeData {
    Page page
    PageType pageType
    String name
    String dataValue
    Date dateCreated
    Date lastUpdated

    static constraints = {
        dataValue nullable: true
    }
}
