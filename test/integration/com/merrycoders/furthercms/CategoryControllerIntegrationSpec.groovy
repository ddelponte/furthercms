package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.converters.JSON
import grails.plugin.spock.IntegrationSpec

class CategoryControllerIntegrationSpec extends IntegrationSpec {
    def setup() {}

    def "delete"() {
        given:
        def page = Page.findByTitle(pageTitle)
        def category = Category.findByPage(page)
        def initialCategoryCount = Category.count()
        def initialPageCount = Page.count()
        def initialModuleCount = Module.count()
        def initialPrimaryCategoryCount = PrimaryCategory.count()

        when:
        new CategoryController().delete(category.id)

        then:
        Category.count() == initialCategoryCount - deletedCategories
        Page.count() == initialPageCount - deletedPages
        Module.count() == initialModuleCount - deletedModules
        PrimaryCategory.count() == initialPrimaryCategoryCount - deletedPrimaryCategories

        where:
        pageTitle          | deletedCategories | deletedPages | deletedModules | deletedPrimaryCategories
        "HTML Child Title" | 1                 | 1            | 1              | 0
        "HTML Title"       | 2                 | 2            | 2              | 0
        "Home Title"       | 3                 | 3            | 3              | 1
    }

    def "move"() {
        given:
        def controller = new CategoryController()
        def category = Category.findByName(childName)
        def parentCategory = Category.findByName(parentName)
        def sibling = new Category(name: "Sibling", parent: parentCategory, urlKey: "${parentCategory?.urlKey}/home-title", page: Page.findByTitle("Home Title"), displayOrder: 99).save()
        def positions = "{0:${sibling.id}, 1:${category?.id}}"
        controller.params.positions = positions

        when:
        controller.move(category.id, parentCategory.id)
        def jsonObject = JSON.parse(controller.response?.text)

        then:
        assert jsonObject?.domainObjects?.size() == domainObjectsSize
        assert jsonObject.firstError?.first() == firstError
        assert jsonObject?.message == message
        assert jsonObject?.success == success
        category?.displayOrder == catgorydisplayOrder
        sibling?.displayOrder == siblingDisplayOrder

        where:
        childName    | parentName   | expectedUrlKey                           | domainObjectsSize | firstError | message        | success | siblingDisplayOrder | catgorydisplayOrder
        "Home"       | "HTML"       | null                                     | 0                 | null       | "Invalid Move" | false   | 99                  | 0
        "Home"       | "HTML Child" | null                                     | 0                 | null       | "Invalid Move" | false   | 99                  | 0
        "HTML"       | "Home"       | "home-title/html-title"                  | 2                 | null       | "Success"      | true    | 0                   | 1
        "HTML"       | "HTML Child" | null                                     | 0                 | null       | "Invalid Move" | false   | 99                  | 0
        "HTML Child" | "Home"       | "home-title/html-child-title"            | 2                 | null       | "Success"      | true    | 0                   | 1
        "HTML Child" | "HTML"       | "home-title/html-title/html-child-title" | 2                 | null       | "Success"      | true    | 0                   | 1
        "Home"       | "Home"       | null                                     | 0                 | null       | "Invalid Move" | false   | 99                  | 0

    }

    def "Invalid move resulting in duplicate url key"() {
        given:
        def controller = new CategoryController()
        def parentCategory = Category.findByName("Home")
        def newCategory = new Category(name: "HTML", parent: Category.findByName("HTML Child"), urlKey: "home-title/html-title/html-child-title/html-title", page: Page.findByTitle("HTML Title"))
        newCategory.save()

        when:
        controller.move(newCategory.id, parentCategory.id)
        def jsonObject = JSON.parse(controller.response?.text)

        then:
        assert jsonObject?.domainObjects?.size() == 2
        assert jsonObject.firstError?.first() == null
        assert jsonObject?.message.contains("The resulting URL must be unique")
        assert jsonObject?.success == false

    }

    def "valid createAndSave"() { // This test is performed here because transactional delete is not working in unit tests (2.2.4)
        given:
        def controller = new CategoryController()
        def parent = Category.findByName(parentName)
        def pageType = pageTypeKey ? PageType.findByPageTypeKey(pageTypeKey) : null
        def title = pageTitle
        def originalChildCount = parent?.children?.size() ?: 0
        def originalPageCount = Page.count()
        def originalModuleCount = Module.count()
        controller.params.pageTypeKey = pageType?.pageTypeKey
        controller.params.title = title
        controller.request.method = "POST"
        controller.request.makeAjaxRequest()

        when:
        controller.createAndSave(parent?.id)
        def results = JSON.parse(controller.response.text)

        then:
        parent?.children?.size() ?: Category.findByUrlKey("")?.children?.size() == originalChildCount + childCountIncrement
        Page.count() == originalPageCount + pageCountIncrement
        Module.count() == originalModuleCount + moduleCountIncrement
        results.success == success

        where:
        parentName      | pageTypeKey     | pageTitle    | childCountIncrement | pageCountIncrement | moduleCountIncrement | success
        "HTML"          | "HTML"          | "New Page"   | 1                   | 1                  | 1                    | true
        "I don't exist" | "I don't exist" | "New Page"   | 2                   | 1                  | 1                    | true
        "I don't exist" | "I don't exist" | ""           | 2                   | 1                  | 1                    | true
        null            | null            | null         | 2                   | 1                  | 1                    | true
        "Site"          | "HTML"          | "Home Title" | 0                   | 0                  | 0                    | false // Results in duplicate URL

    }

}
