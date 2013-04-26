package com.merrycoders.furthercms.bootstrap

class PageTypeBootstrap {

    static init() {

        Core.pageTypePropertyList.each { properties ->
            if (!com.merrycoders.furthercms.PageType.findByPageTypeKey(properties.pageTypeKey)) {
                def pageType = new com.merrycoders.furthercms.PageType()
                pageType.properties = properties
                Core.saveDomainObjects([pageType])
            }
        }

    }
}
