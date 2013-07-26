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

}
