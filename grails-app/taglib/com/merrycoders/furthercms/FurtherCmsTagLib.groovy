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
     * @attr activePrimaryAdminMenuItem The selected PrimaryAdminMenuItemInstance
     */
    def primaryNavAdmin = { attrs, body ->
        PrimaryAdminMenuItem activePrimaryAdminMenuItem = attrs.activePrimaryAdminMenuItem
        def appContext = grailsApplication.config.grails.furthercms.app.context
        def itemList = PrimaryAdminMenuItem.listOrderByDisplayOrder(order: "asc")
        out << render(
                template: "/admin/navigation/primary",
                model: [
                        appContext: appContext,
                        itemList: itemList,
                        activeItem: activePrimaryAdminMenuItem
                ])
    }

    /**
     * @attr activePrimaryAdminMenuItem The selected PrimaryAdminMenuItemInstance
     * @attr activeSecondaryAdminMenuItem The selected SecondaryAdminMenuItemInstance
     */
    def secondaryNavAdmin = { attrs, body ->
        PrimaryAdminMenuItem activePrimaryAdminMenuItem = attrs.activePrimaryAdminMenuItem
        SecondaryAdminMenuItem activeSecondaryAdminMenuItem = attrs.activeSecondaryAdminMenuItem
        def secondaryAdminMenuItemList = SecondaryAdminMenuItem.findAllByPrimaryNavAdminMenuItem(activePrimaryAdminMenuItem, [sort: "displayOrder", order: "asc"]) ?: []
        def appContext = grailsApplication.config.grails.furthercms.app.context
        out << render(
                template: "/admin/navigation/secondary",
                model: [
                        appContext: appContext,
                        itemList: secondaryAdminMenuItemList,
                        activeItem: activeSecondaryAdminMenuItem
                ])
    }

    /**
     * Renders the pages of the site as a tree for easy navigation
     * @attr category The root category of the tree
     * @attr style
     */
    def navTree = { attrs, body ->
        Category rootCategory = attrs?.category
        String style = attrs?.style
        String cssClass = attrs?.class
        List children = rootCategory?.children
        out << render(template: "/admin/navigation/navTree/tree", model: [children: children, style: style, cssClass: cssClass])
    }
    /**
     * Renders the children of the category as a tree
     * @attr category The root category of the tree
     */
    def navTreeChildren = { attrs, body ->
        Category rootCategory = attrs?.category
        List children = rootCategory?.children
        out << render(template: "/admin/navigation/navTree/children", model: [children: children])
    }

}
