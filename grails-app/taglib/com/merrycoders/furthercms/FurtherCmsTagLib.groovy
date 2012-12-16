package com.merrycoders.furthercms

class FurtherCmsTagLib {
    static namespace = "fc"

    /**
     * @attr categoryInstance The Category for the current page being displayed
     */
    def primaryNav = { attrs, body ->
        def categoryInstance = attrs.categoryInstance
        def primaryCategoryInstanceList = PrimaryCategory.list(sort: "displayOrder", order: "asc")
        def activePrimaryNavigationCategoryInstanceList = categoryInstance.primaryCategories
        out << render(
                template: "/public/navigation/primary",
                model: [
                        categoryInstance: categoryInstance,
                        primaryCategoryInstanceList: primaryCategoryInstanceList,
                        activePrimaryNavigationCategoryInstanceList: activePrimaryNavigationCategoryInstanceList
                ])
    }

    def secondaryNav = { attrs, body ->
        out << render(template: "/public/navigation/secondary", model: [:])
    }

}
