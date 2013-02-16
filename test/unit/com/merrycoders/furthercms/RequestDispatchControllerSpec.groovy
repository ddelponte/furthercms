package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(RequestDispatchController)
@Mock([Category, PrimaryCategory, Page, PageType, PrimaryAdminMenuItem, SecondaryAdminMenuItem, ModuleType, Module, ModuleData])
class RequestDispatchControllerSpec extends SpecificationDataCore {
    def setup() {
        controller.categoryService = new CategoryService()
    }

    def "PageType dispatching"() {
        given:
        initAllData()
        params["path"] = path

        when:
        controller.dispatch()

        then:
        response.forwardedUrl == forwardedUrl

        where:
        path                   | forwardedUrl
        "home"                 | "/grails/homePageType/renderPage.dispatch?path=home"
        "home/html"            | "/grails/htmlPageType/renderPage.dispatch?path=home%2Fhtml"
        "home/html/html-child" | "/grails/htmlPageType/renderPage.dispatch?path=home%2Fhtml%2Fhtml-child"

    }
}
