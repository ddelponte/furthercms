package com.merrycoders.furthercms

class RequestDispatchController {
    def categoryService

    def dispatch() {
        def category = categoryService.findByUrlKey(params?.path)
        PageType pageType = category?.page?.pageType
        if (pageType?.pageTypeKey == "home") {
            forward(controller: "homePageType", action: "renderPage")
            return
        } else {
            def page = category?.page
            render(view: "/public/${page?.themeLayout}", model: [categoryInstance: category, pageInstance: page, modules: page?.modules])
        }
    }
}