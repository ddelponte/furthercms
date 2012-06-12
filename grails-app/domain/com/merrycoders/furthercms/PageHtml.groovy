package com.merrycoders.furthercms

class PageHtml {
    String content = ""

    static constraints = {
        content(maxSize: 30000)
    }

    String toString() {
        content
    }
}
