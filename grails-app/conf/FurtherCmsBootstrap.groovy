import com.merrycoders.furthercms.*
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
            initNavAdminMenuItems()
        }
    }

    def destroy = {
    }

    def initAllData() {
        initCategories()
        initNavAdminMenuItems()
    }

    def initPageTypes() {
        def pageTypePropertyList = [
                [name: "HTML Page Type", controller: "htmlPageType", pageTypeKey: "HTML", action: "renderPage"],
                [name: "Home Page Type", controller: "homePageType", pageTypeKey: "home", action: "renderPage"]]

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
        def homePageType = PageType.findByPageTypeKey("home")
        def htmlPageType = PageType.findByPageTypeKey("HTML")
        def homePage = new Page(title: homePageTitle, pageType: homePageType, themeLayout: "home")
        def htmlPage = new Page(title: htmlPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        def htmlChildPage = new Page(title: htmlChildPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        saveDomainObjects([homePage, htmlPage, htmlChildPage])

        def homePageData = new PagePageTypeData(page: homePage, pageType: homePage.pageType, name: "content", dataValue: "<p>Home page content</p>")
        def htmlPageData = new PagePageTypeData(page: htmlPage, pageType: htmlPage.pageType, name: "content", dataValue: "<p>HTML page content</p>")
        def htmlChildPageData = new PagePageTypeData(page: htmlChildPage, pageType: htmlChildPage.pageType, name: "content", dataValue: "<p>HTML child page content</p>")
        saveDomainObjects([homePageData, htmlPageData, htmlChildPageData])
    }

    def initCategories() {
        if (!Page.count()) {
            initPages()
            def root = new Category(name: "Root", urlKey: "")
            def home = new Category(name: "Home", parent: root, urlKey: "home", page: Page.findByTitle(homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "home/html", page: Page.findByTitle(htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "home/html/html-child", page: Page.findByTitle(htmlChildPageTitle))
            def categoryPrimaryInstance = new PrimaryCategory(category: home, displayOrder: 0)
            saveDomainObjects([root, home, html, htmlChild, categoryPrimaryInstance])

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

    private saveDomainObjects(List domainObjects) {
        domainObjects.each { object ->
            if (!object.save(flush: true)) {
                println object.errors.fieldErrors
            }
        }
    }
}