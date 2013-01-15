package com.merrycoders.furthercms

class FurtherCmsTagLib {
    static namespace = "fc"
    def grailsAppllication

    /**
     * @attr categoryInstance The Category for the current page being displayed
     */
    def primaryNav = { attrs, body ->
        def categoryInstance = attrs.categoryInstance
        def primaryCategoryInstanceList = PrimaryCategory.list(sort: "displayOrder", order: "asc")
        def activePrimaryNavigationCategoryInstanceList = categoryInstance.activePrimaryCategories
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/public/navigation/primary",
                model: [
                        appContext: appContext,
                        categoryInstance: categoryInstance,
                        primaryCategoryInstanceList: primaryCategoryInstanceList,
                        activePrimaryNavigationCategoryInstanceList: activePrimaryNavigationCategoryInstanceList
                ])
    }

    def secondaryNav = { attrs, body ->
        def categoryInstance = attrs.categoryInstance
        def secondaryCategoryInstanceList = categoryInstance?.getSecondaryCategories() ?: []
        def activeSecondaryNavigationCategoryIdList = categoryInstance?.ancestry?.id?.intersect(secondaryCategoryInstanceList?.id)?.flatten() ?: []
        def activeSecondaryNavigationCategoryInstanceList = Category.findAllByIdInList(activeSecondaryNavigationCategoryIdList)
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/public/navigation/secondary",
                model: [
                        appContext: appContext,
                        secondaryCategoryInstanceList: secondaryCategoryInstanceList,
                        activeSecondaryNavigationCategoryInstanceList: activeSecondaryNavigationCategoryInstanceList
                ])
    }

    def primaryNavAdmin = { attrs, body ->
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/admin/navigation/primary",
                model: [
                        appContext: appContext,
                        itemList: [[controller: "admin", action: "index", titleMessageCode: "furthercms.admin.primary.navigation.home", titleDefault: "Home"]]
                ])
    }

    def secondaryNavAdmin = { attrs, body ->
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/admin/navigation/secondary",
                model: [
                        appContext: appContext,
                        itemList: [[controller: "admin", action: "index", titleMessageCode: "furthercms.admin.primary.navigation.home", titleDefault: "Home", activeItem: null]]
                ])
    }

}
