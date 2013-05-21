package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.ModuleService
import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PageService

class PageTypeBootstrap {

    static init() {

        CoreBootstrap.pageTypePropertyList.each { properties ->
            if (!com.merrycoders.furthercms.PageType.findByPageTypeKey(properties.pageTypeKey)) {
                def pageType = new com.merrycoders.furthercms.PageType()
                pageType.properties = properties
                CoreBootstrap.saveDomainObjects([pageType])
            }
        }

    }

    def void deleteAllPageReferences(com.merrycoders.furthercms.PageType pageTypeToDelete) {
        def pageService = new PageService()
        pageService.moduleService = new ModuleService()
        def pages = Page.findAllByPageType(pageTypeToDelete)
        pages.each { page ->
            pageService.delete(page)
        }
    }
}
