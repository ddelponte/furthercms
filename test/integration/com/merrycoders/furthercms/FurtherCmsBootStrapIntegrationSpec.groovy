package com.merrycoders.furthercms

import grails.plugin.spock.IntegrationSpec

class FurtherCmsBootStrapIntegrationSpec extends IntegrationSpec {

    def setup() {}

    def "Verify bootstrap objects persisted"() {
        expect:
        PageType.count() == 2
        PageType.countByPageTypeKey("HTML") == 1
        PageType.countByPageTypeKey("REVIEW") == 1
    }
}
