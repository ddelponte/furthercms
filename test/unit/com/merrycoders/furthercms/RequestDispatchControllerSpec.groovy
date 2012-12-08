package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(RequestDispatchController)
@Mock([Category, Page, PageData, PageType])
class RequestDispatchControllerSpec extends SpecificationDataCore {
    def setup() {
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
        path                | forwardedUrl
        ""                  | "/grails/htmlPageType/render.dispatch?path="
        "html"              | "/grails/htmlPageType/render.dispatch?path=html"
        "html/html-child"   | "/grails/htmlPageType/render.dispatch?path=html%2Fhtml-child"

    }
}
