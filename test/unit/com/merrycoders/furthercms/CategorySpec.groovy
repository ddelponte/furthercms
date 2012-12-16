package com.merrycoders.furthercms

import grails.test.mixin.Mock

@Mock([Category, CategoryPrimary, Page, PageType, PagePageTypeData])
class CategorySpec extends SpecificationDataCore {
    def setup() {

    }

    def "getParents"() {
        given:
        initCategories()
        def leafCategory = Category.findByName(rootName)

        when:
        def categoryInstanceList = leafCategory?.getAncestry()

        then:
        categoryInstanceList.size() == expectedResults.size()
        categoryInstanceList.name == expectedResults

        where:
        rootName     | expectedResults
        "HTML Child" | ["Home", "HTML", "HTML Child"]
        "HTML"       | ["Home", "HTML"]
        "Home"       | ["Home"]
    }

    def "getDescendants"() {
        given:
        initCategories()
        def root = Category.findByName(rootName)

        when:
        def categoryInstanceList = root?.getDescendants()

        then:
        categoryInstanceList.size() == expectedResults.size()
        categoryInstanceList.name == expectedResults

        where:
        rootName     | expectedResults
        "Home"       | ["HTML", "HTML Child"]
        "HTML"       | ["HTML Child"]
        "HTML Child" | []
    }
}
