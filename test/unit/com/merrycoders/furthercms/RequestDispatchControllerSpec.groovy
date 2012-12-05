package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(RequestDispatchController)
@Mock([Category, Page, PageData])
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
        ""                  | "/grails/htmlPageType.dispatch?path="
        "html"              | "/grails/htmlPageType.dispatch?path=html"
        "html/html-child"   | "/grails/htmlPageType.dispatch?path=html%2Fhtml-child"

    }
}
