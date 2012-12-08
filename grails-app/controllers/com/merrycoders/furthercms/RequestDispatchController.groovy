package com.merrycoders.furthercms

import org.apache.commons.lang.StringUtils

class RequestDispatchController {
    def categoryService

    def dispatch() {
        def category = categoryService.findByUrlKey(params?.path)
        PageType pageType = category?.page?.pageType
        def pageTypeController = pageType?.controller
        def pageTypeAction = pageType?.action
        forward(controller: pageTypeController, action: pageTypeAction, params: params)
    }

    def index() { }
}
