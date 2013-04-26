package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.plugin.spock.IntegrationSpec

class FurtherCmsBootStrapIntegrationSpec extends IntegrationSpec {

    def setup() {}

    def "Verify bootstrap objects persisted"() {
        expect:
        PageType.count() == 3
        PageType.countByPageTypeKey("HTML") == 1
        PageType.countByPageTypeKey("home") == 1
        PageType.countByPageTypeKey("root") == 1

        Page.count() == 4

        ModuleType.count() == 1
        Module.count() == 4

        PageTypeModuleType.count() == 3

        Category.count() == 4

        PrimaryCategory.count() == 1

        PrimaryAdminMenuItem.count() == 1

        SecondaryAdminMenuItem.count() == 2
    }
}
