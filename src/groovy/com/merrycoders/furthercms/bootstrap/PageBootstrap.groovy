package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.Page

class PageBootstrap {

    static init() {
        if (!com.merrycoders.furthercms.PageType.count()) PageTypeBootstrap.init()
        if (!com.merrycoders.furthercms.ModuleType.count()) ModuleTypeBootstrap.init()
        def rootPageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.ROOT.pageTypeKey)
        def homePageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HOME.pageTypeKey)
        def htmlPageType = com.merrycoders.furthercms.PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        def rootPage = new Page(title: CoreBootstrap.rootPageTitle, pageType: rootPageType, themeLayout: "main")
        def homePage = new Page(title: CoreBootstrap.homePageTitle, pageType: homePageType, themeLayout: "home")
        def htmlPage = new Page(title: CoreBootstrap.htmlPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        def htmlChildPage = new Page(title: CoreBootstrap.htmlChildPageTitle, pageType: htmlPageType, themeLayout: "sidebar")
        CoreBootstrap.saveDomainObjects([rootPage, homePage, htmlPage, htmlChildPage])

        ModuleBootstrap.init([rootPage, homePage, htmlPage, htmlChildPage])
    }
}
