package com.merrycoders.furthercms

class PageTypeModule {
    PageType pageType
    Module module

    static constraints = {
        module unique: "pageType"
    }

    String toString() {
        "PageType: $pageType  Module: $module"
    }
}
