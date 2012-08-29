package com.merrycoders.furthercms

class PageData {
    Page page
    String name
    String dataValue
    Date dateCreated
    Date lastUpdated

    static constraints = {
        dataValue nullable: true
    }
}
