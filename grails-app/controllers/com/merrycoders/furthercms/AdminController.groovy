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

        def model = [activePrimaryAdminMenuItem: activePrimaryAdminMenuItem, activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem]
        render(view: "/admin/index", model: model)
    }

    def edit(Long id) {
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByControllerAndAction("admin", "pages")

        def category = Category.get(id)
        def page = category.page
        def pageType = page.pageType

        def model = [
                activePrimaryAdminMenuItem: activePrimaryAdminMenuItem,
                activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem,
                categoryInstance: category,
                pageType: pageType,
                action: "edit"]

        render(view: "/admin/index", model: model)
    }
}
