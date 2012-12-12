package com.merrycoders.furthercms

class NavigationService {

    /**
     * Return a list of menu items to be rendered in the primary navigation area of the public website
     */
    def getPrimary() {
        Category.findAllIsInPrimaryNavigation()
    }

    /**
     * Return a list of menu items to be rendered in the secondary navigation area of the public website
     */
    def getSecondary(Category category) {
        if (!category) return []
        Category.findAllIsInSecondaryNavigationByParent(category, [sort: "name"])
    }

    /**
     * Return a list of menu items to be rendered in the user navigation area of the public website
     */
    def getUser() {}

}
