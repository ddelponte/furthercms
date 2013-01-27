package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(FurtherCmsTagLib)
@Mock([Category, PrimaryCategory, Page, PageType, PagePageTypeData, PrimaryAdminMenuItem, SecondaryAdminMenuItem])
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
        results.contains("<p:callTag tag=\"g:link\" attrs=\"{url=/furthercms/home/html}\">")
        results.contains("<nav:title item=\"{titleMessageCode=com.merrycoders.furthercms.category, titleDefault=HTML}\"></nav:title>")

        where:
        activeCategoryName << ["HTML Child", "HTML"]
    }

    def "admin primary nav construction"() {
        given:
        initNavAdminMenuItems()
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByTitleDefault("Home")

        when:
        def results = tagLib.primaryNavAdmin([activePrimaryAdminMenuItem: activePrimaryAdminMenuItem])

        then:
        results.contains("<li class=\"active\">")
        results.contains("<p:callTag tag=\"g:link\" attrs=\"{url=/furthercms/admin/index}\">")
        results.contains("<nav:title item=\"{titleMessageCode=furthercms.admin.primary.navigation.home, titleDefault=Home}\"></nav:title>")
    }

    def "admin secondary nav construction"() {
        given:
        initNavAdminMenuItems()
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByTitleDefault("Home")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByTitleDefault("Pages")

        when:
        def results = tagLib.secondaryNavAdmin([activePrimaryAdminMenuItem: activePrimaryAdminMenuItem, activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem])

        then:
        results.contains("<li class=\"active\">")
        results.contains("<p:callTag tag=\"g:link\" attrs=\"{url=/furthercms/admin/pages}\">")
        results.contains("<nav:title item=\"{titleMessageCode=furthercms.admin.primary.navigation.pages, titleDefault=Pages}\"></nav:title>")
    }

    def "navTree construction"() {
        given:
        initCategories()
        def rootCategory = Category.findByUrlKey("")

        when:
        def results = tagLib.navTree([category: rootCategory])

        then:
        results.contains("<li id=\"category_2\">")
        results.contains("<a href=\"home\">Home</a>")
        results.contains("<li id=\"category_3\">")
        results.contains("<a href=\"home/html\">HTML</a>")
        results.contains("<li id=\"category_4\">")
        results.contains("<a href=\"home/html/html-child\">HTML Child</a>")

    }
}