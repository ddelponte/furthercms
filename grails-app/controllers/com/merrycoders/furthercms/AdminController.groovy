package com.merrycoders.furthercms

class AdminController {

    def index() {
//        if (s.ifLoggedIn()) {
//            render "logged in"
//        } else {
//            redirect(url: "${s.createLoginLink()}")
//        }
        def activePrimaryAdminMenuItem = PrimaryNavAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryNavAdminMenuItem.findByControllerAndAction("admin", "pages")

        [activePrimaryAdminMenuItem: activePrimaryAdminMenuItem, activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem]
    }

    def pages() {

    }
}
