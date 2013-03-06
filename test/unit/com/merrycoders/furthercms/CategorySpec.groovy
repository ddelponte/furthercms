package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock

@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, ModuleType, Module, HtmlModule])
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

    def "getPrimaryCategories"() {
        given:
        initCategories()
        def childCategory = Category.findByName("HTML Child")

        when:
        def primaryCategoryInstanceList = childCategory.activePrimaryCategories

        then:
        primaryCategoryInstanceList.size() == 1
        primaryCategoryInstanceList.name == ["Home"]

        where:
        childCategoryName | expectedResults
        "HTML Child"      | ["Home"]
        "HTML"            | ["Home"]
        "Home"            | ["Home"]
        "DNE"             | []

    }
}
