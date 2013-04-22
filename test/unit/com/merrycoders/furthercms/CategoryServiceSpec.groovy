package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(CategoryService)
@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, Module, ModuleType, HtmlModule])
class CategoryServiceSpec extends SpecificationDataCore {

    def setup() {
        def categoryService = initCategoryService()
        service.pageService = categoryService.pageService
        service.utilityService = categoryService.utilityService
        service.primaryCategoryService = categoryService.primaryCategoryService
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
        category.name == name
        category.description == description
        category.parent == parentCategory
        category.urlKey == urlKey
        category.page == page
        category.parent == parentCategory
        category.isPublished == false
        PrimaryCategory.countByCategory(category) == categoryPrimaryCount

        where:
        urlKey                                   | name         | description | parentUrlKey            | pageTitle          | categoryPrimaryCount
        ""                                       | "Site"       | null        | null                    | null               | 0
        "home-title"                             | "Home"       | null        | ""                      | "Home Title"       | 1
        "home-title/html-title"                  | "HTML"       | null        | "home-title"            | "HTML Title"       | 0
        "home-title/html-title/html-child-title" | "HTML Child" | null        | "home-title/html-title" | "HTML Child Title" | 0
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
        savedCategory.displayOrder == 1
        savedCategory.siblings.size() == 1
        savedPrimaryCategory.displayOrder == 1
        savedPrimaryCategory.siblings.size() == 1
    }

    def "move"() {
        given:
        initCategories()
        def category = Category.findByName(childName)
        def parentCategory = Category.findByName(parentName)

        when:
        def results = service.move(category, parentCategory)

        then:
        results?.urlKey == expectedUrlKey

        where:
        childName    | parentName   | expectedUrlKey
        "Home"       | "HTML"       | null
        "Home"       | "HTML Child" | null
        "HTML"       | "Home"       | "home-title/html-title"
        "HTML"       | "HTML Child" | null
        "HTML Child" | "Home"       | "home-title/html-child-title"
        "HTML Child" | "HTML"       | "home-title/html-title/html-child-title"
        "Home"       | "Home"       | null

    }
}
