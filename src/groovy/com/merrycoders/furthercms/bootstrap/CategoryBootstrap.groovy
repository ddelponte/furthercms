package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PrimaryCategory

class CategoryBootstrap {

    static init() {
        if (!Page.count()) {
            PageBootstrap.init()
            def root = new com.merrycoders.furthercms.Category(name: Core.rootCategoryName, urlKey: "", page: Page.findByTitle(Core.rootPageTitle))
            def home = new com.merrycoders.furthercms.Category(name: Core.homeCategoryName, parent: root, urlKey: "home-title", page: Page.findByTitle(Core.homePageTitle))
            def html = new com.merrycoders.furthercms.Category(name: Core.htmlCategoryName, parent: home, urlKey: "home-title/html-title", page: Page.findByTitle(Core.htmlPageTitle), isInSecondaryNavigation: true)
            def htmlChild = new com.merrycoders.furthercms.Category(name: Core.htmlChildCategoryName, parent: html, urlKey: "home-title/html-title/html-child-title", page: Page.findByTitle(Core.htmlChildPageTitle))
            def categoryPrimaryInstance = new PrimaryCategory(category: home, displayOrder: 0)
            Core.saveDomainObjects([root, home, html, htmlChild, categoryPrimaryInstance])

        }
    }
}
