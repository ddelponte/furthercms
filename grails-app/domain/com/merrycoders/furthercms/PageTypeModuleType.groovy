package com.merrycoders.furthercms

class PageTypeModuleType {
    PageType pageType
    ModuleType moduleType
    Date dateCreated
    Date lastUpdated

    static constraints = {
        moduleType unique: "pageType"
    }

    static PageTypeModuleType create(PageType pageType, ModuleType moduleType, boolean flush = false) {
        if (!pageType || !moduleType) return null

        def pageTypeModuleType = PageTypeModuleType.findByPageTypeAndModuleType(pageType, moduleType)
        if (!pageTypeModuleType) {
            pageTypeModuleType = new PageTypeModuleType(pageType: pageType, moduleType: moduleType).save(flush: flush)
        }

        return pageTypeModuleType

    }

    String toString() {
        "PageType: $pageType  ModuleType: $moduleType"
    }
}
