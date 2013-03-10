package com.merrycoders.furthercms

class ErrorController {

    def index() {
        if (request.xhr) {
            response.status = 200
            render template: "/error/ajaxError", model: [code: params.code]
        } else {
            render view: "/error/pageError", model: [code: params.code]
        }
    }
}
