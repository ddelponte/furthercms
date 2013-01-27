package com.merrycoders.furthercms

class AdminController {

    def index() {
//        if (s.ifLoggedIn()) {
//            render "logged in"
//        } else {
//            redirect(url: "${s.createLoginLink()}")
//        }
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByControllerAndAction("admin", "pages")

        [activePrimaryAdminMenuItem: activePrimaryAdminMenuItem, activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem]
    }

    def edit(Long id) {

    }
}
