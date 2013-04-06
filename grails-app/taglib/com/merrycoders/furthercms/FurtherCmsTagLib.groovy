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
        secondaryAdminMenuItemList = secondaryAdminMenuItemList.size() > 1 ? secondaryAdminMenuItemList : []
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
     * @attr cssClass
     * @attr selectedNodeId The id of the Category to highlight in the tree
     */
    def navTree = { attrs, body ->
        Category rootCategory = attrs?.category
        String style = attrs?.style
        String cssClass = attrs?.class
        String selectedNodeId = attrs?.selectedNodeId
        List children = rootCategory?.children
        out << render(template: "/admin/navigation/navTree/tree", model: [children: children, style: style, cssClass: cssClass, selectedNodeId: selectedNodeId])
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

    /**
     * Takes a list of Module instances and renders them
     * @module Module instance to be rendered
     */
    def renderModule = { attrs, body ->
        def module = attrs?.module
        def viewFolder = module?.moduleType?.viewFolder
        out << render(template: "/modules/${viewFolder}/public", model: [module: module])
    }

    def renderModuleEdit = { attrs, body ->
        def module = attrs?.module
        def viewFolder = module?.moduleType?.viewFolder
        out << render(template: "/modules/${viewFolder}/edit", model: [module: module])
    }

    def htmlEditor = { attrs, body ->
        def name = attrs?.name
        def height = attrs?.height
        def width = attrs?.width
        def data = attrs?.data ?: ""

        out << render(template: "/modules/html/editor", model: [name: name, height: height, width: width, data: data])
    }

    def categoryEditor = { attrs, body ->
        Category category = attrs.category
        Page page = attrs.page
        out << render(template: "/modules/category/editor", model: [category: category, page: page])
    }

    def reorderModulesButton = { attrs, body ->
        out << render(template: "/admin/components/reorderModulesButton")
    }

}
