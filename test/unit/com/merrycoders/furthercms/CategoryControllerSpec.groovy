package com.merrycoders.furthercms

import com.merrycoders.furthercms.ajax.AjaxPostResponse
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
        def page = Page.findByTitle(htmlChildPageTitle)
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
        controller.urlKey(category.id)

        then:
        response.text == urlKey

        where:
        title              | urlKey
        htmlChildPageTitle | "home-title/html-title/html-child-title"
        "I don't exist"    | ""


    }

}
