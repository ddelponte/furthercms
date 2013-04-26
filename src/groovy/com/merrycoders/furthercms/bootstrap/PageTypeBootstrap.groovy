package com.merrycoders.furthercms.bootstrap

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
}
