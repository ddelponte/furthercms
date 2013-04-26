package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.plugin.spock.IntegrationSpec

class ModuleServiceIntegrationSpec extends IntegrationSpec {
    def moduleService

    def setup() {}

    def "test module deletion"() {
        given:
        def module
        def originalModuleCount = Module.count()
        if (!isModuleNull) module = Module.list().first()

        when:
        moduleService.delete([module?.id])

        then:
        Module.count() == originalModuleCount - expectedDeletions

        where:
        isModuleNull | expectedDeletions
        false        | 1
        true         | 0
    }
}
