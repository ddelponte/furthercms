package com.merrycoders.furthercms

import spock.lang.Specification

/**
 * This acts as a central test data repository for all Specification tests.  The goal is to reduce code duplication.
 */
class SpecificationDataCore extends Specification {
    static def homePageTitle = "Home Title"
    static def htmlPageTitle = "HTML Title"
    static def htmlChildPageTitle = "HTML Child Title"

    def initAllData() {
        initCategories()
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
            def home = new Category(name: "Home", urlKey: "", page: Page.findByTitle(homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "html", page: Page.findByTitle(htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "html/html-child", page: Page.findByTitle(htmlChildPageTitle))
            def categoryPrimaryInstance = new CategoryPrimary(category: home, displayOrder: 0)
            saveDomainObjects([home, html, htmlChild, categoryPrimaryInstance])
        }
    }

    private saveDomainObjects(List domainObjects) {
        domainObjects.each { object ->
            if (!object.save()) {
                println object.errors.fieldErrors
            }
        }
    }

}
