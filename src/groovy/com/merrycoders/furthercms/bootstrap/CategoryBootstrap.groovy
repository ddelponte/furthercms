package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PrimaryCategory

class CategoryBootstrap {

    static init() {
        if (!Page.count()) {
            PageBootstrap.init()
            def root = new com.merrycoders.furthercms.Category(name: CoreBootstrap.rootCategoryName, urlKey: "", page: Page.findByTitle(CoreBootstrap.rootPageTitle))
            def home = new com.merrycoders.furthercms.Category(name: CoreBootstrap.homeCategoryName, parent: root, urlKey: "home-title", page: Page.findByTitle(CoreBootstrap.homePageTitle))
            def html = new com.merrycoders.furthercms.Category(name: CoreBootstrap.htmlCategoryName, parent: home, urlKey: "home-title/html-title", page: Page.findByTitle(CoreBootstrap.htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new com.merrycoders.furthercms.Category(name: CoreBootstrap.htmlChildCategoryName, parent: html, urlKey: "home-title/html-title/html-child-title", page: Page.findByTitle(CoreBootstrap.htmlChildPageTitle))
            def categoryPrimaryInstance = new PrimaryCategory(category: home, displayOrder: 0)
            CoreBootstrap.saveDomainObjects([root, home, html, htmlChild, categoryPrimaryInstance])

        }
    }
}
