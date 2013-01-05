package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(CategoryService)
@Mock([Category, PrimaryCategory, PageType, Page, PagePageTypeData])
class CategoryServiceSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    def "find by URL key"() {
        given:
        initCategories()

        when:
        def category = service.findByUrlKey(urlKey)

        then:
        def page = Page.findByTitle(pageTitle)
        def parentCategory
        if (parentUrlKey != null) parentCategory = Category.findByUrlKey(parentUrlKey)
        category.name == name
        category.description == description
        category.parent == parentCategory
        category.urlKey == urlKey
        category.page == page
        category.parent == parentCategory
        category.isPublished == false
        PrimaryCategory.countByCategory(category) == categoryPrimaryCount

        where:
        urlKey            | name         | description | parentUrlKey | pageTitle          | categoryPrimaryCount
        ""                | "Home"       | null        | null         | "Home Title"       | 1
        "html"            | "HTML"       | null        | ""           | "HTML Title"       | 0
        "html/html-child" | "HTML Child" | null        | "html"       | "HTML Child Title" | 0
    }

    def "verify diplayOrder of category is properly set when saved"() {
        given:
        initCategories()
        def parentCategoryInstance = Category.findByName("HTML")
        def newCategoryInstance = new Category(name: "New Category Instnace", parent: parentCategoryInstance, urlKey: "html/new-category-instance", page: Page.findByTitle(htmlChildPageTitle))
        assert newCategoryInstance.displayOrder == 0

        when:
        def savedCategory = service.save(newCategoryInstance)
        def newPrimaryCategoryInstance = new PrimaryCategory(category: newCategoryInstance)
        assert newPrimaryCategoryInstance.displayOrder == null
        def savedPrimaryCategory = service.save(newPrimaryCategoryInstance)

        then:
        savedCategory.displayOrder == 1
        savedCategory.siblings.size() == 1
        savedPrimaryCategory.displayOrder == 1
        savedPrimaryCategory.siblings.size() == 1
    }
}
