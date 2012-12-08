package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(CategoryService)
@Mock([Category, PageType, Page, PageData])
class CategoryServiceSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
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
        category.isInMenu == false

        where:
        urlKey            | name         | description | parentUrlKey | pageTitle
        ""                | "Home"       | null        | null         | "Home Title"
        "html"            | "HTML"       | null        | ""           | "HTML Title"
        "html/html-child" | "HTML Child" | null        | "html"       | "HTML Child Title"


    }
}
