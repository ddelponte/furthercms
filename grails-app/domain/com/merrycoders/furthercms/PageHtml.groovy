package com.merrycoders.furthercms

class PageHtml {
    String content = ""
    Date dateCreated
    Date lastUpdated

    static constraints = {
        content(maxSize: 30000)
    }

    String toString() {
        content
    }
}
