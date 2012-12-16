import com.merrycoders.furthercms.Category
import com.merrycoders.furthercms.CategoryPrimary
import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PagePageTypeData
import com.merrycoders.furthercms.PageType
import grails.util.Environment

class FurtherCmsBootStrap {
    def homePageTitle = "Home Title"
    def htmlPageTitle = "HTML Title"
    def htmlChildPageTitle = "HTML Child Title"

    def init = { servletContext ->
        if (Environment.developmentMode || Environment.current == Environment.TEST) {
            initAllData()
        } else {
            initPageTypes()
        }
    }

    def destroy = {
    }

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
            if (!object.save(flush: true)) {
                println object.errors.fieldErrors
            }
        }
    }
}