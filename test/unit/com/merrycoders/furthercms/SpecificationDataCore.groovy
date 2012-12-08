package com.merrycoders.furthercms

import spock.lang.Specification

/**
 * This acts as a central test data repository for all Specification tests.  The goal is to reduce code duplication.
 */
class SpecificationDataCore extends Specification {
    def homePageTitle = "Home Title"
    def htmlPageTitle = "HTML Title"
    def htmlChildPageTitle = "HTML Child Title"

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
        def homePage = new Page(title: homePageTitle, pageType: htmlPageType)
        def htmlPage = new Page(title: htmlPageTitle, pageType: htmlPageType)
        def htmlChildPage = new Page(title: htmlChildPageTitle, pageType: htmlPageType)
        saveDomainObjects([homePage, htmlPage, htmlChildPage])

        def homePageData = new PageData(page: homePage, name: "content", dataValue: "<p>Home page content</p>")
        def htmlPageData = new PageData(page: htmlPage, name: "content", dataValue: "<p>HTML page content</p>")
        def htmlChildPageData = new PageData(page: htmlChildPage, name: "content", dataValue: "<p>HTML child page content</p>")
        saveDomainObjects([homePageData, htmlPageData, htmlChildPageData])
    }

    def initCategories() {
        if (Page.count() == 0) {
            initPages()
            def home = new Category(name: "Home", urlKey: "", page: Page.findByTitle(homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "html", page: Page.findByTitle(htmlPageTitle))
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "html/html-child", page: Page.findByTitle(htmlChildPageTitle))
            saveDomainObjects([home, html, htmlChild])
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
