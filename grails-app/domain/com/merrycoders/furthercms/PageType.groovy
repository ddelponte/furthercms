package com.merrycoders.furthercms

public enum PageType {
    HTML("HTML"),
    REVIEW("Review")

    String name
    String description = ""

    PageType(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    static constraints = {
    }

    def getPages() {
        Page.findByPageType(this, [sort: "title"])
    }
}