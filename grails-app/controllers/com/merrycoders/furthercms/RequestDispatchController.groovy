package com.merrycoders.furthercms

import org.apache.commons.lang.StringUtils

class RequestDispatchController {

    def dispatch() {
        def sanitizedPath = params?.path as String
        sanitizedPath = StringUtils.stripEnd(sanitizedPath, "/")
        def category = Category.findByUrlKey(sanitizedPath, [fetch: [page: "join"]])
        PageType pageType = category?.page?.pageType
        def pageTypeController = pageType?.controller
        def pageTypeAction = pageType?.action
        forward(controller: pageTypeController, action: pageTypeAction, params: params)
    }

    def index() { }
}
