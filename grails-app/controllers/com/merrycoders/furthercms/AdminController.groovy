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
        def contentTemplate = "/admin/defaultBody"

        def model = [
                activePrimaryAdminMenuItem: activePrimaryAdminMenuItem,
                activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem,
                contentTemplate: contentTemplate]

        render(view: "/admin/index", model: model)
    }

    def edit(Long id) {
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByControllerAndAction("admin", "pages")

        def category = Category.findById(id, [fetch: [page: "join"]])
        def page = category.page
        def pageType = page.pageType
        def modules = page?.modules

        def contentTemplate = "/admin/pageType/${pageType?.controller}/edit"

        def model = [
                activePrimaryAdminMenuItem: activePrimaryAdminMenuItem,
                activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem,
                contentTemplate: contentTemplate,
                categoryInstance: category,
                pageInstance: page,
                pageType: pageType,
                modules: modules]

        render(view: "/admin/index", model: model)
    }
}
