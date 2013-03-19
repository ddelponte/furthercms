package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
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
    def primaryCategoryService

    def setup() {
        categoryService = new CategoryService()
        categoryService.pageService = new PageService()
        categoryService.utilityService = new UtilityService()
        primaryCategoryService = new PrimaryCategoryService()
    }

    def initAllData() {
        initCategories()
        initNavAdminMenuItems()
    }

    def initModuleTypes() {
        if (!ModuleType.count()) {
            def moduleType = new ModuleType(name: "HTML", className: HtmlModule.class.name, code: "com.merrycoders.furthercms.moduletype.html")
            saveDomainObjects([moduleType])
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
                [name: "Home Page Type", pageTypeKey: "home"]]

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

        initModules([homePage, htmlPage, htmlChildPage])
    }

    def initCategories() {
        if (Page.count() == 0) {
            initPages()
            def root = new Category(name: "Root", urlKey: "")
            def home = new Category(name: "Home", parent: root, urlKey: "home-title", page: Page.findByTitle(homePageTitle))
            def html = new Category(name: "HTML", parent: home, urlKey: "home-title/html-title", page: Page.findByTitle(htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new Category(name: "HTML Child", parent: html, urlKey: "home-title/html-title/html-child-title", page: Page.findByTitle(htmlChildPageTitle))
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
                if (category instanceof Category) {
                    categoryService.save(category)
                } else {
                    primaryCategoryService.save(category)
                }
                // Primary Category
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

            def secondaryNavAdminMenuItem2 = new SecondaryAdminMenuItem(
                    primaryNavAdminMenuItem: primaryNavAdminMenuItem,
                    titleMessageCode: "furthercms.admin.primary.navigation.pages",
                    titleDefault: "Admin",
                    controller: "admin",
                    action: "admin",
                    displayOrder: 0
            )

            saveDomainObjects([primaryNavAdminMenuItem, secondaryNavAdminMenuItem, secondaryNavAdminMenuItem2])
        }
    }

}
