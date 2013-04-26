package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.PrimaryAdminMenuItem
import com.merrycoders.furthercms.SecondaryAdminMenuItem

class NavAdminMenuItemsBootstrap {

    static init() {

        if (!PrimaryAdminMenuItem.count()) {
            def primaryNavAdminMenuItem = new PrimaryAdminMenuItem(
                    titleMessageCode: "furthercms.admin.primary.navigation.home",
                    titleDefault: "Home",
                    controller: "admin",
                    action: "index",
                    displayOrder: 0
            )

            def secondaryNavAdminMenuItem = new SecondaryAdminMenuItem(
                    primaryNavAdminMenuItem: primaryNavAdminMenuItem,
                    titleMessageCode: "furthercms.admin.primary.navigation.pages",
                    titleDefault: "Pages",
                    controller: "admin",
                    action: "pages",
                    displayOrder: 0
            )

            Core.saveDomainObjects([primaryNavAdminMenuItem, secondaryNavAdminMenuItem])
        }

    }

}
