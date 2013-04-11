package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ModuleTypeController)
@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, PrimaryAdminMenuItem, SecondaryAdminMenuItem, ModuleType, Module, HtmlModule])
class ModuleTypeControllerSpec extends SpecificationDataCore {
    def setup() {

        controller.moduleService = new ModuleService()
        controller.metaClass.fc = mockFurtherCmsTagLib()
    }

    def populateValidParams(params) {
        assert params != null
        params["id"] = "${ModuleType.findByName("HTML").id}"
        params["page.id"] = "${Page.findByTitle(htmlChildPageTitle).id}"
    }

    def "test renderModuleEdit"() {
        given:
        initAllData()
        populateValidParams(params)

        when:
        controller.renderModuleEdit()

        then:
        response.text == "['moduleEditTag':'HTML']"

    }
}
