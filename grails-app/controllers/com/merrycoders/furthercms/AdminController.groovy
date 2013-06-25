package com.merrycoders.furthercms

class AdminController {

    def index() {
//        if (s.ifLoggedIn()) {
//            render "logged in"
//        } else {
//            redirect(url: "${s.createLoginLink()}")
//        }

        def chainModel = request.chainModel ?: [:]
        def secondaryAdminMenuItemController = chainModel?.params?.controller ?: "admin"
        def secondaryAdminMenuItemAction = chainModel?.params?.action ?: "index"

        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByControllerAndAction(secondaryAdminMenuItemController, secondaryAdminMenuItemAction)

        def model = [
                activePrimaryAdminMenuItem: activePrimaryAdminMenuItem,
                activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem,
                contentTemplatePath: contentTemplatePath]

        model += chainModel

        render(view: "/admin/index", model: model)
    }

    def edit(Long id) {
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByControllerAndAction("admin", "index")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByControllerAndAction("admin", "index")

        def category = Category.findById(id, [fetch: [page: "join"]])
        def page = category?.page
        def pageType = page?.pageType
        def modules = page?.modules
        def contentTemplatePath = category?.urlKey == "" ? getContentTemplatePath() : "/admin/primaryAdminMenuItems/pages/edit"

        def model = [
                activePrimaryAdminMenuItem: activePrimaryAdminMenuItem,
                activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem,
                categoryInstance: category,
                pageInstance: page,
                pageType: pageType,
                modules: modules,
                contentTemplatePath: contentTemplatePath]

        render(view: "/admin/index", model: model)
    }

    private def getContentTemplatePath() {
        return params?.contentTemplatePath ?: "/admin/primaryAdminMenuItems/pages/noPage"
    }
}
