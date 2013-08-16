package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.PageTypeBootstrap
import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeController)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, HtmlModule, Category, PrimaryCategory, PageTypeModuleType])
class PageTypeControllerSpec extends SpecificationDataCore {

    def setup() {
        controller.pageTypeService = new PageTypeService()
        controller.pageTypeModuleTypeService = new PageTypeModuleTypeService()
        controller.metaClass.displayFlashMessage = { LinkedHashMap args -> return args.text }
    }

    def cleanup() {}

    def "undeletable PageType"() {
        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        def originalPageTypeCount = PageType.count()

        when:
        controller.delete(pageType.id)

        then:
        response.redirectedUrl == "/pageType/edit/${pageType.id}"
        PageType.count() == originalPageTypeCount

    }

    def "delete"() {
        given:
        initCategories()
        def originalPageTypeCount = PageType.count()
        def pageTypeToDelete = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        new PageTypeBootstrap().deleteAllPageReferences(pageTypeToDelete)

        when:
        controller.delete(pageTypeToDelete.id)

        then:
        response.redirectedUrl == "/pageType/list"
        PageType.count() == originalPageTypeCount - 1

    }

    def "edit"() {
        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        when:
        controller.edit(pageType.id)

        then:
        response.forwardedUrl == "/grails/admin/index.dispatch"

    }

    def "updateModuleTypes"() {

        given:
        initCategories()
        def pageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        def groupedModuleTypes = initPageTypeModuleTypes(pageType)
        def activeModuleTypes = groupedModuleTypes[PageTypeModuleTypeStatus.ACTIVE]
        def availableModuleTypes = groupedModuleTypes[PageTypeModuleTypeStatus.AVAILABLE]
        def unavailableModuleTypes = groupedModuleTypes[PageTypeModuleTypeStatus.UNAVAILABLE]

        def activeJsonString = buildModuleTypeJsonString(activeModuleTypes, PageTypeModuleTypeStatus.ACTIVE)
        def availableJsonString = buildModuleTypeJsonString(availableModuleTypes, PageTypeModuleTypeStatus.AVAILABLE)
        def unavailableJsonString = buildModuleTypeJsonString(unavailableModuleTypes, PageTypeModuleTypeStatus.UNAVAILABLE)

        def originalActiveCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        def originalAvailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        def originalUnavailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

        controller.params["Active"] = activeJsonString
        controller.params["Available"] = availableJsonString
        controller.params["Unavailable"] = unavailableJsonString

        when:
        controller.updateModuleTypes(pageType.id)

        then:
        originalActiveCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        originalAvailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        originalUnavailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)
        response.redirectedUrl == "/pageType/edit/1"

    }
}