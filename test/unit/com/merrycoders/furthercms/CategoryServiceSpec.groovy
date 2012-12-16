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

    void "find by URL key"() {
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
}
