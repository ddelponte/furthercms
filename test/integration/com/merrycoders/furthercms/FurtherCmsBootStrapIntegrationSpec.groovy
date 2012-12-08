package com.merrycoders.furthercms

import grails.plugin.spock.IntegrationSpec

class FurtherCmsBootStrapIntegrationSpec extends IntegrationSpec {

    def setup() {}

    def "Verify bootstrap objects persisted"() {
        expect:
        PageType.count() == 1
        PageType.countByPageTypeKey("HTML") == 1
    }
}
