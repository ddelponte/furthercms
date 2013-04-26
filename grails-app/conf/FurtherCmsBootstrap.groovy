import com.merrycoders.furthercms.Category
import com.merrycoders.furthercms.ModuleType
import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PageType
import com.merrycoders.furthercms.PageTypeModuleType
import com.merrycoders.furthercms.PrimaryAdminMenuItem
import com.merrycoders.furthercms.PrimaryCategory
import com.merrycoders.furthercms.SecondaryAdminMenuItem
import com.merrycoders.furthercms.bootstrap.Core
import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.util.Environment

class FurtherCmsBootStrap {

    def init = { servletContext ->
        if (Environment.developmentMode || Environment.current == Environment.TEST) {
            initAllData()
        } else {
            initModuleTypes()
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

    def initModuleTypes() {
        if (!ModuleType.count()) {
            def moduleType = new ModuleType(name: "HTML", className: HtmlModule.class.name, code: "com.merrycoders.furthercms.moduletype.html")
            Core.saveDomainObjects([moduleType])
        }
    }

    def initModules(List<Page> pages) {
        if (!ModuleType.count()) initModuleTypes()
        pages.each { page ->
            def moduleType = ModuleType.findByClassName(HtmlModule.class.name)
            def module = Module.create([moduleType: moduleType, page: page, html: "<p>Where are we going?</p>", flush: true])
            PageTypeModuleType.create(page.pageType, module.moduleType)
        }
    }

    def initPageTypes() {
        def pageTypePropertyList = [
                [name: "HTML Page Type", pageTypeKey: "HTML"],
                [name: "Home Page Type", pageTypeKey: "home"],
                [name: "Root Page Type", pageTypeKey: "root", description: "Placeholder for a node representing the root node of the website"]]

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
        if (!ModuleType.count()) initModuleTypes()
        def rootPageType = PageType.findByPageTypeKey("root")
        def homePageType = PageType.findByPageTypeKey("home")
        def htmlPageType = PageType.findByPageTypeKey("HTML")

        def rootPage = new Page(title: Core.rootPageTitle, pageType: rootPageType, themeLayout: "main")
        def homePage = new Page(title: Core.homePageTitle, pageType: homePageType, themeLayout: "home")
        def htmlPage = new Page(title: Core.htmlPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        def htmlChildPage = new Page(title: Core.htmlChildPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        Core.saveDomainObjects([rootPage, homePage, htmlPage, htmlChildPage])

        initModules([rootPage, homePage, htmlPage, htmlChildPage])
    }

    def initCategories() {
        if (!Page.count()) {
            initPages()
            def root = new Category(name: "Site", urlKey: "", page: Page.findByTitle(Core.rootPageTitle))
            def home = new Category(name: "Home", parent: root, urlKey: "home-title", page: Page.findByTitle(Core.homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "home-title/html-title", page: Page.findByTitle(Core.htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "home-title/html-title/html-child-title", page: Page.findByTitle(Core.htmlChildPageTitle))
            def categoryPrimaryInstance = new PrimaryCategory(category: home, displayOrder: 0)
            Core.saveDomainObjects([root, home, html, htmlChild, categoryPrimaryInstance])

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

            Core.saveDomainObjects([primaryNavAdminMenuItem, secondaryNavAdminMenuItem])
        }
    }
}