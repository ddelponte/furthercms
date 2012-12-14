package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(CategoryService)
@Mock([Category, PageType, Page, PagePageTypeData])
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
        category.isInPrimaryNavigation == isInPrimaryNavigation

        where:
        urlKey            | name         | description | parentUrlKey | pageTitle          | isInPrimaryNavigation
        ""                | "Home"       | null        | null         | "Home Title"       | true
        "html"            | "HTML"       | null        | ""           | "HTML Title"       | false
        "html/html-child" | "HTML Child" | null        | "html"       | "HTML Child Title" | false


    }
}
