package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import com.merrycoders.furthercms.modules.Module
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(CategoryController)
@Mock([Category, Page, PageType, Module, ModuleType, PageTypeModuleType, PrimaryCategory])
class CategoryControllerSpec extends SpecificationDataCore {
    def utilityService

    def setup() {
        utilityService = new UtilityService()
        utilityService.metaClass.preparePostResponse = { List domainInstances ->
            return new AjaxPostResponse()
        }
        controller.utilityService = utilityService
        controller.categoryService = categoryService
        controller.moduleService = new ModuleService()
    }

    def "update"() {
        given:
        initCategories()
        def page = Page.findByTitle(CoreBootstrap.htmlChildPageTitle)
        page.title = title
        def category = Category.findByPage(page)
        category.utilityService = utilityService
        def categoryUpdateCommand = new CategoryUpdateCommand(category: category, page: page, modulesToDelete: "{}")
        def parentCategory = category.parent
        request.method = "POST"
        request.makeAjaxRequest()

        when:
        controller.update(categoryUpdateCommand)
        def modifiedCategory = Category.findByPage(page)
        def modifiedPage = modifiedCategory.page
        def results = JSON.parse(response.text)

        then:
        results.success == isSuccessful

        modifiedCategory.urlKey == urlKey
        modifiedCategory.name == category.name
        modifiedCategory.description == null
        modifiedCategory.parent == parentCategory
        modifiedCategory.isPublished == false
        modifiedCategory.isInSecondaryNavigation == false
        modifiedCategory.code == "com.merrycoders.furthercms.category"
        modifiedCategory.displayOrder == 0

        modifiedPage.title == title
        modifiedPage.metaDescription == null
        modifiedPage.metaKeywords == null
        modifiedPage.linkText == null
        modifiedPage.isPublished == false
        modifiedPage.isInMenu == true
        modifiedPage.isHidden == false
        modifiedPage.themeLayout == "sidebar"

        where:
        title          | isSuccessful | urlKey
        "My new title" | true         | "home-title/html-title/my-new-title"
        ""             | true         | "home-title/html-title/html-child-title"

    }

    def "urlKey retrieval"() {
        given:
        initCategories()
        def page = Page.findByTitle(title)
        def category = Category.findByPage(page)

        when:
        controller.urlKey(category?.id)

        then:
        response.text == urlKey

        where:
        title                            | urlKey
        CoreBootstrap.htmlChildPageTitle | "home-title/html-title/html-child-title"
        "I don't exist"                  | ""
        CoreBootstrap.rootPageTitle      | ""


    }

    def "valid createAndSave"() {
        given:
        initCategories()
        def parent = Category.findByName(parentName)
        def pageType = pageTypeName ? PageType.findByName(pageTypeName) : null
        def title = pageTitle
        def originalChildCount = parent?.children?.size() ?: 0
        def originalPageCount = Page.count()
        def originalModuleCount = Module.count()
        params.pageTypeKey = pageType?.pageTypeKey
        params.title = title
        request.method = "POST"
        request.makeAjaxRequest()

        when:
        controller.createAndSave(parent?.id)
        def results = JSON.parse(response.text)

        then:
        parent?.children?.size() ?: Category.findByUrlKey("")?.children?.size() == originalChildCount + childCountIncrement
        Page.count() == originalPageCount + pageCountIncrement
        Module.count() == originalModuleCount + moduleCountIncrement
        results.success == success

        where:
        parentName      | pageTypeName    | pageTitle    | childCountIncrement | pageCountIncrement | moduleCountIncrement | success
        "HTML"          | "HTML"          | "New Page"   | 1                   | 1                  | 1                    | true
        "I don't exist" | "I don't exist" | "New Page"   | 2                   | 1                  | 1                    | true
        "I don't exist" | "I don't exist" | ""           | 2                   | 1                  | 1                    | true
        null            | null            | null         | 2                   | 1                  | 1                    | true
        "Site"          | "HTML"          | "Home Title" | 0                   | 0                  | 0                    | false // Results in duplicate URL

    }

}
