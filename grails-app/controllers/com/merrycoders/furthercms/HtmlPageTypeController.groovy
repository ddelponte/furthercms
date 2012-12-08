package com.merrycoders.furthercms

import org.apache.commons.lang.StringUtils

class HtmlPageTypeController {
    def categoryService

    /**
     * Render the full HTML page
     */
    def renderPage(String path) {
        def category = categoryService.findByUrlKey(path)
        println category
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
