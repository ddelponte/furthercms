package com.merrycoders.furthercms

import com.merrycoders.furthercms.exceptions.UndeletablePageTypeException
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeService)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, Category, PrimaryCategory])
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
        deleteAllPageReferences(pageTypeToDelete)

        when:
        service.delete(pageTypeToDelete)

        then:
        PageType.count() == originalPageTypeCount - 1
    }

    private deleteAllPageReferences(PageType pageTypeToDelete) {
        def pageService = new PageService()
        pageService.moduleService = new ModuleService()
        def pages = Page.findAllByPageType(pageTypeToDelete)
        pages.each { page ->
            pageService.delete(page)
        }
    }
}
