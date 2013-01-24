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

    /**
     * @attr activePrimaryMenuItem The selected PrimaryMenuItemInstance
     */
    def primaryNavAdmin = { attrs, body ->
        PrimaryNavAdminMenuItem activePrimaryMenuItem = attrs.activePrimaryMenuItem
        def appContext = grailsApplication.config.grails.furthercms.app.context
        def itemList = PrimaryNavAdminMenuItem.listOrderByDisplayOrder(order: "asc")
        out << render(
                template: "/admin/navigation/primary",
                model: [
                        appContext: appContext,
                        itemList: itemList,
                        activeItem: activePrimaryMenuItem
                ])
    }

    def secondaryNavAdmin = { attrs, body ->
        PrimaryNavAdminMenuItem activePrimaryNavAdminMenuItem = attrs.activePrimaryNavAdminMenuItem
        SecondaryNavAdminMenuItem activeSecondaryNavAdminMenuItem = attrs.activeSecondaryNavAdminMenuItem
        def secondaryNavAdminMenuItemList = SecondaryNavAdminMenuItem.findAllByPrimaryNavAdminMenuItem(activePrimaryNavAdminMenuItem) ?: []
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/admin/navigation/secondary",
                model: [
                        appContext: appContext,
                        itemList: secondaryNavAdminMenuItemList,
                        activeItem: activeSecondaryNavAdminMenuItem
                ])
    }

}
