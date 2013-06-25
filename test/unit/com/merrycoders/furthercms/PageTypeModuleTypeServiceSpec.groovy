package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeModuleTypeService)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, Category, PrimaryCategory, PageTypeModuleType])
class PageTypeModuleTypeServiceSpec extends SpecificationDataCore {

    def setup() {}

    def cleanup() {}

    def "updateModuleTypes"() {
        given:
        initCategories()
        def pageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        def groupedModuleTypeIds = convertValuesToIds(initPageTypeModuleTypes(pageType))

        def originalActiveCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        def originalAvailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        def originalUnavailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

        when:
        service.updateByModuleTypesById(pageType, groupedModuleTypeIds)

        then:
        originalActiveCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        originalAvailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        originalUnavailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

    }

    def "Verify ordering of active module types"() {
        given:
        initCategories()
        def pageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        def groupedModuleTypeIds = convertValuesToIds(initPageTypeModuleTypes(pageType))
        def activePageTypeModuleTypes = PageTypeModuleType.findAllByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE, [sort: "displayOrder", order: "asc"])
        groupedModuleTypeIds[(PageTypeModuleTypeStatus.ACTIVE)] = activePageTypeModuleTypes.moduleType.id.reverse()

        def originalActiveCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        def originalAvailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        def originalUnavailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

        when:
        service.updateByModuleTypesById(pageType, groupedModuleTypeIds)

        then:
        originalActiveCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        originalAvailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        originalUnavailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)
        activePageTypeModuleTypes.reverse()?.id == PageTypeModuleType.findAllByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE, [sort: "displayOrder", order: "asc"])?.id

    }

    /**
     * Converts a map of type <PageTypeModuleTypeStatus, ModuleType> to <PageTypeModuleTypeStatus, Long>
     * @param groupedModuleTypes
     * @return
     */
    private Map convertValuesToIds(Map<PageTypeModuleTypeStatus, ModuleType> groupedModuleTypes) {
        def convertedMap = [:]

        groupedModuleTypes.each { status, moduleTypeList ->
            def convertedList = moduleTypeList?.collect { it?.id }
            convertedMap[status] = convertedList
        }

        return convertedMap
    }
}
