package com.merrycoders.furthercms

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(ModuleService)
@Mock([])
class ModuleServiceSpec extends SpecificationDataCore {

    def setup() {}

    def "convertFormInputToModules"() {
        given:
        def x = 1

        when:
        def moduleList = service.convertFormInputToModules(key, value)

        then:
        moduleList.id == [1]
        moduleList.version == [0]
        moduleList.displayOrder == [1]
        moduleList.dataKey == ["html"]
        moduleList.dataValue == ["<p>Where we going?</p>"]

        where:
        key | value
        "id_3_version_0_dataKey_html_displayOrder_1" | "<p>Where we going?</p>"


    }
}
