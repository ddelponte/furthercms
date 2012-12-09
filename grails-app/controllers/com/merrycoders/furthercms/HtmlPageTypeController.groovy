package com.merrycoders.furthercms

class HtmlPageTypeController {
    def categoryService

    /**
     * Render the full HTML page
     */
    def renderPage(String path) {
        def category = categoryService.findByUrlKey(path)
        def page = category?.page
        render(view: "/public/${page?.themeLayout}")
    }

    def list() {

    }

    def create() {

    }

    def save() {

    }

    def edit() {

    }

    def update() {

    }

    def delete() {

    }
}
