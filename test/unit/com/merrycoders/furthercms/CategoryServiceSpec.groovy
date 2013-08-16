package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import com.merrycoders.furthercms.exceptions.InvalidCategoryMoveException
import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.validation.ValidationException

@TestFor(CategoryService)
@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, Module, ModuleType, HtmlModule])
class CategoryServiceSpec extends SpecificationDataCore {

    def setup() {
        def categoryService = initCategoryService()
        service.moduleService = categoryService.moduleService
        service.pageService = categoryService.pageService
        service.primaryCategoryService = categoryService.primaryCategoryService
        service.utilityService = categoryService.utilityService
    }

    def cleanup() {
    }

    def "find by URL key"() {
        given:
        initCategories()

        when:
        def category = service.findByUrlKey(urlKey)
        def page = pageTitle ? Page.findByTitle(pageTitle) : Page.findByTitleIsNull()
        def parentCategory = null
        if (parentUrlKey != null) parentCategory = Category.findByUrlKey(parentUrlKey)

        then:
        category?.name == name
        category?.description == description
        category?.parent == parentCategory
        category?.urlKey == urlKey
        category?.page == page
        category?.parent == parentCategory
        category?.isPublished == isPublished
        PrimaryCategory.countByCategory(category) == categoryPrimaryCount

        where:
        urlKey                                   | name         | description | parentUrlKey            | pageTitle          | categoryPrimaryCount | isPublished
        null                                     | null         | null        | null                    | null               | 0                    | null
        ""                                       | "Site"       | null        | null                    | "Site"             | 0                    | false
        "home-title"                             | "Home"       | null        | ""                      | "Home Title"       | 1                    | false
        "home-title/html-title"                  | "HTML"       | null        | "home-title"            | "HTML Title"       | 0                    | false
        "home-title/html-title/html-child-title" | "HTML Child" | null        | "home-title/html-title" | "HTML Child Title" | 0                    | false
    }

    def "verify diplayOrder of category is properly set when saved"() {
        given:
        initCategories()
        def parentCategoryInstance = Category.findByName("HTML")
        def page = new Page(title: "New Category Instance").save(flush: true)
        def newCategoryInstance = new Category(name: "New Category Instance", parent: parentCategoryInstance, urlKey: "html/new-category-instance", page: page)
        assert newCategoryInstance.displayOrder == 0

        when:
        def savedCategory = service.save(newCategoryInstance)
        def newPrimaryCategoryInstance = new PrimaryCategory(category: newCategoryInstance)
        assert newPrimaryCategoryInstance.displayOrder == null
        def savedPrimaryCategory = new PrimaryCategoryService().save(newPrimaryCategoryInstance)

        then:
        savedCategory.displayOrder == 0
        savedCategory.siblings.size() == 1
        savedPrimaryCategory.displayOrder == 1
        savedPrimaryCategory.siblings.size() == 1
    }

    def "Valid category moves"() {
        given:
        initCategories()
        def category = Category.findByName(childName)
        def parentCategory = Category.findByName(parentName)
        def sibling = new Category(name: "Sibling", parent: parentCategory, urlKey: "${parentCategory?.urlKey}/home-title", page: Page.findByTitle("Home Title"), displayOrder: 99).save()
        def positions = "{0:${sibling.id}, 1:${category?.id}}"

        when:
        def results = service.move(category, parentCategory, positions)

        then:
        results?.urlKey == expectedUrlKey
        category.displayOrder == catgorydisplayOrder
        sibling.displayOrder == siblingDisplayOrder

        where:
        childName    | parentName | expectedUrlKey                           | siblingDisplayOrder | catgorydisplayOrder
        "HTML"       | "Home"     | "home-title/html-title"                  | 0                   | 1
        "HTML Child" | "Home"     | "home-title/html-child-title"            | 0                   | 1
        "HTML Child" | "HTML"     | "home-title/html-title/html-child-title" | 0                   | 1

    }

    def "Invalid category moves, moving parent to child"() {
        given:
        initCategories()
        def category = Category.findByName(childName)
        def parentCategory = Category.findByName(parentName)
        def sibling = new Category(name: "Sibling", parent: parentCategory, urlKey: "${parentCategory?.urlKey}/home-title", page: Page.findByTitle("Home Title"), displayOrder: 99).save()
        def positions = "{0:${sibling.id}, 1:${category?.id}}"

        when: "The user moves a category to one of its children"
        service.move(category, parentCategory, positions)

        then:
        InvalidCategoryMoveException ex = thrown()
        ex.message == "Invalid Move"
        category.displayOrder == catgorydisplayOrder
        sibling.displayOrder == siblingDisplayOrder

        where:
        childName | parentName   | expectedUrlKey | siblingDisplayOrder | catgorydisplayOrder
        "Home"    | "HTML"       | null           | 99                  | 0
        "Home"    | "HTML Child" | null           | 99                  | 0
        "HTML"    | "HTML Child" | null           | 99                  | 0
        "Home"    | "Home"       | null           | 99                  | 0
    }

    def "Invalid category moves, move would result in duplicate URLs"() {
        given:
        initCategories()
        def parentCategory = Category.findByName("Home")
        def newCategory = new Category(name: "HTML", parent: Category.findByName("HTML Child"), urlKey: "home-title/html-title/html-child-title/html-title", page: Page.findByTitle(CoreBootstrap.htmlPageTitle))
        newCategory.save()
        def sibling = new Category(name: "Sibling", parent: parentCategory, urlKey: "${parentCategory?.urlKey}/home-title", page: Page.findByTitle("Home Title"), displayOrder: 99).save()
        def positions = "{0:${sibling.id}, 1:${newCategory?.id}}"

        when:
        service.move(newCategory, parentCategory, positions)

        then: "This move results in duplicate url keys"
        ValidationException ex = thrown()
        ex.message.contains("urlKey.unique.error")
        sibling.displayOrder == 99
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
        def properties = [page: [title: title, pageType: pageType, themeLayout: "sidebar"], category: [parent: parent], flush: true]

        when:
        service.createAndSave(properties)

        then:
        parent?.children?.size() ?: Category.findByUrlKey("")?.children?.size() == originalChildCount + childCountIncrement
        Page.count() == originalPageCount + pageCountIncrement
        Module.count() == originalModuleCount + moduleCountIncrement

        where:
        parentName | pageTypeName     | pageTitle  | childCountIncrement | pageCountIncrement | moduleCountIncrement | success
        "HTML"     | "HTML Page Type" | "New Page" | 1                   | 1                  | 1                    | true

    }

    def "invalid createAndSave"() {
        given:
        initCategories()
        def parent = Category.findByName(parentName)
        def pageType = pageTypeName ? PageType.findByName(pageTypeName) : null
        def title = pageTitle
        def originalChildCount = parent?.children?.size() ?: 0
        def originalPageCount = Page.count()
        def originalModuleCount = Module.count()
        def properties = [page: [title: title, pageType: pageType, themeLayout: "sidebar"], category: [parent: parent], flush: true]

        when:
        service.createAndSave(properties)

        then:
        ValidationException ex = thrown()
        ex.message.contains("Page is not valid")
        ex.message.contains("Field error in object 'com.merrycoders.furthercms.Page' on field 'pageType'")
        ex.message.contains("com.merrycoders.furthercms.Page.pageType.nullable.error.com.merrycoders.furthercms.Page.pageType")
        parent?.children?.size() ?: Category.findByUrlKey("")?.children?.size() == originalChildCount + childCountIncrement
        Page.count() == originalPageCount
        Module.count() == originalModuleCount

        where:
        parentName      | pageTypeName    | pageTitle    | childCountIncrement
        "I don't exist" | "I don't exist" | "New Page"   | 1
        "I don't exist" | "I don't exist" | ""           | 1
        null            | null            | null         | 1
        "Site"          | "HTML"          | "Home Title" | 1 // Results in duplicate URL

    }
}
