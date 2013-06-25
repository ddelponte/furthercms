package com.merrycoders.furthercms

class PageTypeModuleTypeService {

    /**
     *
     * @param pageType
     * @param groupedModuleTypeIds Map containing ModuleType id Maps grouped by status.  For example:
     *
     * [PageTypeModuleTypeStatus.ACTIVE : [1, 2, 3], PageTypeModuleTypeStatus.AVAILABLE : [...], ...]
     * @return List of PageTypeModuleType instances
     */
    List updateByModuleTypesById(PageType pageType, Map<PageTypeModuleTypeStatus, Long> groupedModuleTypeIds) {

        def pageTypeModuleTypeList = []

        groupedModuleTypeIds.each { pageTypeModuleTypeStatus, moduleTypeIds ->

            def moduleTypeList = ModuleType.findAllByIdInList(moduleTypeIds)

            moduleTypeList.each { moduleType ->

                def pageTypeModuleType = PageTypeModuleType.findByPageTypeAndModuleType(pageType, moduleType)
                pageTypeModuleType.status = pageTypeModuleTypeStatus
                pageTypeModuleTypeList << pageTypeModuleType.save(flush: true)

            }

        }

        def activeModuleTypeIds = groupedModuleTypeIds[PageTypeModuleTypeStatus.ACTIVE]
        updateDisplayOrder(activeModuleTypeIds, pageType)

        return pageTypeModuleTypeList

    }

    private updateDisplayOrder(List<Long> moduleTypeIds, PageType pageType) {
        def activeModuleTypes = ModuleType.findAllByIdInList(moduleTypeIds)
        def pageTypeModuleTypes = PageTypeModuleType.findAllByPageTypeAndModuleTypeInList(pageType, activeModuleTypes)

        moduleTypeIds.eachWithIndex { id, index ->

            def pageTypeModuleType = pageTypeModuleTypes.find { it?.moduleType?.id == id }
            pageTypeModuleType?.displayOrder = index
            pageTypeModuleType?.save(flush: true)

        }
    }
}
