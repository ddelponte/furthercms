package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(RequestDispatchController)
@Mock([Category, Page, PagePageTypeData, PageType])
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
        path              | forwardedUrl
        ""                | "/grails/htmlPageType/renderPage.dispatch?path="
        "html"            | "/grails/htmlPageType/renderPage.dispatch?path=html"
        "html/html-child" | "/grails/htmlPageType/renderPage.dispatch?path=html%2Fhtml-child"

    }
}
