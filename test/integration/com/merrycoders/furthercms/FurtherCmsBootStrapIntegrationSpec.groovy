package com.merrycoders.furthercms

import grails.plugin.spock.IntegrationSpec

class FurtherCmsBootStrapIntegrationSpec extends IntegrationSpec {

    def setup() {}

    def "Verify bootstrap objects persisted"() {
        expect:
        PageType.count() == 2
        PageType.countByPageTypeKey("HTML") == 1
        PageType.countByPageTypeKey("home") == 1

        Page.count() == 3
        PagePageTypeData.count() == 3

        Category.count() == 4

        PrimaryCategory.count() == 1

        PrimaryAdminMenuItem.count() == 1

        SecondaryAdminMenuItem.count() == 1
    }
}
