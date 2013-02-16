package com.merrycoders.furthercms

class HomePageTypeController {

    /**
     * Render the full HTML page
     */
    def renderPage() {
        def homePageType = PageType.findByPageTypeKey("home")
        def page = Page.findByPageType(homePageType)
        def category = Category.findByPage(page)
        render(view: "/public/${page?.themeLayout}", model: [categoryInstance: category, pageInstance: page, modules: page.modules])
    }

    def edit() {

    }

    def update() {

    }
}
