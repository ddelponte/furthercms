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

    def "primary nav construction"() {
        given:
        initCategories()
        def categoryInstance = Category.findByName("HTML Child")

        when:
        def results = tagLib.primaryNav([categoryInstance: categoryInstance])

        then:
        results.contains("<li class=\"active\">")
        results.contains("<nav:title item=\"{titleMessageCode=com.merrycoders.furthercms.category, titleDefault=Home}")
    }

    def "secondary nav construction"() {
        given:
        initCategories()
        def leafCategory = Category.findByName(activeCategoryName)

        when:
        def results = tagLib.secondaryNav([categoryInstance: leafCategory])

        then:
        results.contains("class=\"nav nav-pills\"")
        results.contains("<li class=\"active\">")
        results.contains("<p:callTag tag=\"g:link\" attrs=\"{url=/furthercms/html}\">")
        results.contains("<nav:title item=\"{titleMessageCode=com.merrycoders.furthercms.category, titleDefault=HTML}\"></nav:title>")

        where:
        activeCategoryName << ["HTML Child", "HTML"]
    }
}