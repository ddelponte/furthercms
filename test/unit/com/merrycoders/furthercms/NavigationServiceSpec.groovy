package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(NavigationService)
@Mock([Category, Page, PageType, PagePageTypeData])
class NavigationServiceSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    def "Primary Navigation"() {
        given:
        initCategories()

        when:
        def results = service.getPrimary()

        then:
        Category.findAllByName("Home") == results
    }

    def "Secondary Navigation"() {
        given:
        initCategories()
        def homeCategory = Category.findByName("Home")

        when:
        def results = service.getSecondary(homeCategory)

        then:
        Category.findAllByName("HTML") == results
    }
}
