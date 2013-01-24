package com.merrycoders.furthercms

class AdminController {

    def index() {
//        if (s.ifLoggedIn()) {
//            render "logged in"
//        } else {
//            redirect(url: "${s.createLoginLink()}")
//        }
        def activePrimaryNavAdminMenuItem = PrimaryNavAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryNavAdminMenuItem = SecondaryNavAdminMenuItem.findByControllerAndAction("admin", "pages")

        [activePrimaryMenuItem: activePrimaryNavAdminMenuItem, activeSecondaryMenuItem: activeSecondaryNavAdminMenuItem]
    }

    def pages() {

    }
}
