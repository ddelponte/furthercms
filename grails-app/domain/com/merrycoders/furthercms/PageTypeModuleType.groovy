package com.merrycoders.furthercms

class PageTypeModuleType {
    PageType pageType
    ModuleType moduleType
    PageTypeModuleTypeStatus status
    Integer displayOrder // The default order in which active modules should be displayed in new pages of this PageType
    Date dateCreated
    Date lastUpdated

    static constraints = {
        moduleType unique: "pageType"
        displayOrder nullable: true
    }

    static PageTypeModuleType create(PageType pageType, ModuleType moduleType, status = PageTypeModuleTypeStatus.AVAILABLE, boolean flush = false) {
        if (!pageType || !moduleType) return null

        def pageTypeModuleType = PageTypeModuleType.findByPageTypeAndModuleType(pageType, moduleType)
        if (!pageTypeModuleType) {
            Integer displayOrder = PageTypeModuleType.findAllByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)?.displayOrder?.max() ?: 0
            displayOrder++
            pageTypeModuleType = new PageTypeModuleType(pageType: pageType, moduleType: moduleType, status: status, displayOrder: displayOrder).save(flush: flush)
        }

        return pageTypeModuleType

    }

    String toString() {
        "PageType: $pageType  ModuleType: $moduleType"
    }
}
