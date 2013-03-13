package com.merrycoders.furthercms.ajax

class AjaxPostResponse {
    Boolean success = true
    String message = "Success"
    String html
    def domainObjects = []
    def errors = [:]
}