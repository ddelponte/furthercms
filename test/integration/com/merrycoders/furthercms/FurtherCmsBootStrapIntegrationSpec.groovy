package com.merrycoders.furthercms

import grails.plugin.spock.IntegrationSpec

class FurtherCmsBootStrapIntegrationSpec extends IntegrationSpec {

    def setup() {}

    def "Verify bootstrap objects persisted"() {
        expect:
        PageType.count() == 1
        PageType.countByPageTypeKey("HTML") == 1

        Page.count() == 3
        PagePageTypeData.count() == 3

        Category.count() == 3

        PrimaryCategory.count() == 1

        PrimaryNavAdminMenuItem.count() == 1
    }
}
