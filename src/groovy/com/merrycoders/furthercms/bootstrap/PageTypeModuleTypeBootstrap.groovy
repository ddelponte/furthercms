package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.PageTypeModuleType
import com.merrycoders.furthercms.PageTypeModuleTypeStatus

class PageTypeModuleTypeBootstrap {

    /**
     *
     * @param pageType
     * @return Map of PageTypeModuleTypeStatus and associated ModuleTypes.  For example:
     * [PageTypeModuleTypeStatus.ACTIVE: [ModuleTypeInstance_1, ModuleTypeInstance2, ...], PageTypeModuleTypeStatus.UNAVAILABLE: [...]]
     */
    public static initPageTypeModuleTypes(com.merrycoders.furthercms.PageType pageType) {

        if (!pageType) pageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        PageTypeModuleTypeStatus.values().eachWithIndex { pageTypeModuleTypeStatus, index ->
            5.times {
                def moduleType = new com.merrycoders.furthercms.ModuleType(name: "Module Type $it $index", className: "bogus.class$it$index", code: "bogus.code",)
                CoreBootstrap.saveDomainObjects([moduleType])
                PageTypeModuleType.create(pageType, moduleType, pageTypeModuleTypeStatus)
            }

        }

        def activeModuleTypes
        def availableModuleTypes
        def unavailableModuleTypes

        PageTypeModuleTypeStatus.values().each { pageTypeModuleTypeStatus ->
            def pageTypeModuleTypes = PageTypeModuleType.findAllByPageType(pageType)
            activeModuleTypes = pageTypeModuleTypes.findAll { it.status == PageTypeModuleTypeStatus.ACTIVE }?.moduleType
            availableModuleTypes = pageTypeModuleTypes.findAll { it.status == PageTypeModuleTypeStatus.AVAILABLE }?.moduleType
            unavailableModuleTypes = pageTypeModuleTypes.findAll { it.status == PageTypeModuleTypeStatus.UNAVAILABLE }?.moduleType
        }

        return [
                (PageTypeModuleTypeStatus.ACTIVE): activeModuleTypes,
                (PageTypeModuleTypeStatus.AVAILABLE): availableModuleTypes,
                (PageTypeModuleTypeStatus.UNAVAILABLE): unavailableModuleTypes
        ]

    }

}
