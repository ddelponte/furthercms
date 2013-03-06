package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(ModuleService)
@Mock([Category, PrimaryCategory, PageType, Page, ModuleType, Module, ModuleData])
class ModuleServiceSpec extends SpecificationDataCore {

    def setup() {}

    def "convertFormInputToModules"() {
        given:
        initPages()
        def pageInstance = Page.findByTitle(htmlChildPageTitle)

        when:
        def moduleList = service.convertFormInputToModules(pageInstance, ["${key}": value])

        then:
        moduleList.id == [pageInstance.id]
        moduleList.version == [pageInstance.version + 1]
        moduleList.displayOrder == [1]
        moduleList.moduleData.dataKey == [["html"]]
        moduleList.moduleData.dataValue == [[value]]

        where:
        key                                                                                            | value
        "id_3_version_0_displayOrder_1_moduleTypeId_1_moduleDataId_3_moduleDataVersion_0_dataKey_html" | "<p>Where we going?</p>"


    }
}
