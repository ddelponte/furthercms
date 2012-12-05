package com.merrycoders.furthercms

/**
 * Describes the type of page to be rendered and supplies the name of the controller that will handle the rendering
 */
public enum PageType {
    HTML("HTML", "htmlPageType"),
    REVIEW("Review", "reviewPageType")

    String name
    String controller
    String description = ""

    PageType(String name, String controller) {
        this.name = name
        this.controller = controller
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