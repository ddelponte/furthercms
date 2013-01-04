package com.merrycoders.furthercms

class PageType {
    String name
    String controller
    String action
    String pageTypeKey
    String description = ""
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name unique: true
        pageTypeKey unique: true
        description maxSize: 30000
    }

    static mapping = {
        cache: true
        index: 'Name_Idx'
        controller: "Controller_Idx"
        pageTypeKey: "PageTypeKey_Idx"
    }

    String toString() {
        name
    }
}
