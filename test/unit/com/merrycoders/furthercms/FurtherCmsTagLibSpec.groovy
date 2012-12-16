package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(FurtherCmsTagLib)
@Mock([Category, PrimaryCategory, Page, PageType, PagePageTypeData])
class FurtherCmsTagLibSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        initCategories()
        def categoryInstance = Category.findByName("HTML Child")

        when:
        def results = tagLib.primaryNav([categoryInstance: categoryInstance])

        then:
        results.contains("<div id=\"yeppers\"")
    }
}