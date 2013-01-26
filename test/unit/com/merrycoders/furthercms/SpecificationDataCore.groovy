package com.merrycoders.furthercms

import grails.validation.ValidationException
import spock.lang.Specification

/**
 * This acts as a central test data repository for all Specification tests.  The goal is to reduce code duplication.
 */
class SpecificationDataCore extends Specification {
    static def homePageTitle = "Home Title"
    static def htmlPageTitle = "HTML Title"
    static def htmlChildPageTitle = "HTML Child Title"
    def categoryService

    def setup() {
        categoryService = new CategoryService()
    }

    def initAllData() {
        initCategories()
        initNavAdminMenuItems()
    }

    def initPageTypes() {
        def pageTypePropertyList = [
                [name: "HTML Page Type", controller: "htmlPageType", pageTypeKey: "HTML", action: "renderPage"]]

        pageTypePropertyList.each { properties ->
            if (!PageType.findByPageTypeKey(properties.pageTypeKey)) {
                def pageType = new PageType()
                pageType.properties = properties
                if (!pageType.save(flush: true)) {
                    log.error "Unable to save PageType ${pageType} due to errors: ${pageType?.errors?.fieldErrors}"
                }
            }
        }
    }

    def initPages() {
        if (!PageType.count()) initPageTypes()
        def htmlPageType = PageType.findByPageTypeKey("HTML")
        def homePage = new Page(title: homePageTitle, pageType: htmlPageType, themeLayout: "home")
        def htmlPage = new Page(title: htmlPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        def htmlChildPage = new Page(title: htmlChildPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        saveDomainObjects([homePage, htmlPage, htmlChildPage])

        def homePageData = new PagePageTypeData(page: homePage, pageType: homePage.pageType, name: "content", dataValue: "<p>Home page content</p>")
        def htmlPageData = new PagePageTypeData(page: htmlPage, pageType: htmlPage.pageType, name: "content", dataValue: "<p>HTML page content</p>")
        def htmlChildPageData = new PagePageTypeData(page: htmlChildPage, pageType: htmlChildPage.pageType, name: "content", dataValue: "<p>HTML child page content</p>")
        saveDomainObjects([homePageData, htmlPageData, htmlChildPageData])
    }

    def initCategories() {
        if (Page.count() == 0) {
            initPages()
            def root = new Category(name: "Root", urlKey: "")
            def home = new Category(name: "Home", parent: root, urlKey: "home", page: Page.findByTitle(homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "home/html", page: Page.findByTitle(htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "home/html/html-child", page: Page.findByTitle(htmlChildPageTitle))
            def categoryPrimaryInstance = new PrimaryCategory(category: home, displayOrder: 0)
            saveCategoryInstances([root, home, html, htmlChild, categoryPrimaryInstance])
        }
    }

    private saveDomainObjects(List domainObjects) {
        domainObjects.each { object ->
            if (!object.save()) {
                println object.errors.fieldErrors
            }
        }
    }

    private saveCategoryInstances(List categoryInstanceList) {
        categoryInstanceList.each { category ->
            try {
                categoryService.save(category)
            } catch (ValidationException e) {
                println e.errors
            }
        }
    }

    def initNavAdminMenuItems() {
        if (!PrimaryAdminMenuItem.count()) {

            def primaryNavAdminMenuItem = new PrimaryAdminMenuItem(
                    titleMessageCode: "furthercms.admin.primary.navigation.home",
                    titleDefault: "Home",
                    controller: "admin",
                    action: "index",
                    displayOrder: 0
            )

            def secondaryNavAdminMenuItem = new SecondaryAdminMenuItem(
                    primaryNavAdminMenuItem: primaryNavAdminMenuItem,
                    titleMessageCode: "furthercms.admin.primary.navigation.pages",
                    titleDefault: "Pages",
                    controller: "admin",
                    action: "pages",
                    displayOrder: 0
            )

            saveDomainObjects([primaryNavAdminMenuItem, secondaryNavAdminMenuItem])
        }
    }

}
