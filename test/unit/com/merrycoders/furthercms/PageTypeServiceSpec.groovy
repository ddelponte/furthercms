package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.PageTypeBootstrap
import com.merrycoders.furthercms.exceptions.UndeletablePageTypeException
import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeService)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, HtmlModule, Category, PrimaryCategory])
class PageTypeServiceSpec extends SpecificationDataCore {

    def setup() {}

    def cleanup() {}

    def "Undeletable page type"() {
        given:
        initCategories()
        def originalPageTypeCount = PageType.count()
        def pageTypeToDelete = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        when:
        service.delete(pageTypeToDelete)

        then:
        UndeletablePageTypeException ex = thrown()
        ex.message == "Unable to delete page type at this time"
        PageType.count() == originalPageTypeCount
    }

    def "delete"() {
        given:
        initCategories()
        def originalPageTypeCount = PageType.count()
        def pageTypeToDelete = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        new PageTypeBootstrap().deleteAllPageReferences(pageTypeToDelete)

        when:
        service.delete(pageTypeToDelete)

        then:
        PageType.count() == originalPageTypeCount - 1
    }

    def "findAllModuleTypesByPageType"() {
        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        when:
        def results = service.findAllModuleTypesByPageType(pageType)

        then:
        results[PageTypeModuleTypeStatus.ACTIVE].size() == 1
        results[PageTypeModuleTypeStatus.AVAILABLE].size() == 0
        results[PageTypeModuleTypeStatus.UNAVAILABLE].size() == 0

    }

}
