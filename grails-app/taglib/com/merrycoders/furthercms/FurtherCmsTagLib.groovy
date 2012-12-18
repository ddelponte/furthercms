package com.merrycoders.furthercms

class FurtherCmsTagLib {
    static namespace = "fc"

    /**
     * @attr categoryInstance The Category for the current page being displayed
     */
    def primaryNav = { attrs, body ->
        def categoryInstance = attrs.categoryInstance
        def primaryCategoryInstanceList = PrimaryCategory.list(sort: "displayOrder", order: "asc")
        def activePrimaryNavigationCategoryInstanceList = categoryInstance.activePrimaryCategories
        out << render(
                template: "/public/navigation/primary",
                model: [
                        categoryInstance: categoryInstance,
                        primaryCategoryInstanceList: primaryCategoryInstanceList,
                        activePrimaryNavigationCategoryInstanceList: activePrimaryNavigationCategoryInstanceList
                ])
    }

    def secondaryNav = { attrs, body ->
        def categoryInstance = attrs.categoryInstance
        def secondaryCategoryInstanceList = categoryInstance?.getSecondaryCategories() ?: []
        def activeSecondaryNavigationCategoryIdList = categoryInstance?.ancestry?.id?.intersect(secondaryCategoryInstanceList?.id) ?: []
        def activeSecondaryNavigationCategoryInstanceList = Category.getAll(activeSecondaryNavigationCategoryIdList)
        out << render(
                template: "/public/navigation/secondary",
                model: [
                        secondaryCategoryInstanceList: secondaryCategoryInstanceList,
                        activeSecondaryNavigationCategoryInstanceList: activeSecondaryNavigationCategoryInstanceList
                ])
    }

}
