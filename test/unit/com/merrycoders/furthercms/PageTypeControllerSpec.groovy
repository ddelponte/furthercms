package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.PageTypeBootstrap
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeController)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, Category, PrimaryCategory])
class PageTypeControllerSpec extends SpecificationDataCore {

    def setup() {
        controller.pageTypeService = new PageTypeService()
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
}