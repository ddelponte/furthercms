package com.merrycoders.furthercms

class AdminController {

    def index() {
        if (s.ifLoggedIn()) {
            render "logged in"
        } else {
            redirect(url: "${s.createLoginLink()}")
        }
    }
}
