package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.plugin.spock.IntegrationSpec

class ModuleServiceIntegrationSpec extends IntegrationSpec {
    def moduleService

    def setup() {}

    def "test module deletion"() {
        given:
        def module
        if (!isModuleNull) module = Module.list().first()

        when:
        moduleService.delete([module?.id])

        then:
        Module.count() == expectedResults

        where:
        isModuleNull | expectedResults
        false        | 2
        true         | 3
    }
}
