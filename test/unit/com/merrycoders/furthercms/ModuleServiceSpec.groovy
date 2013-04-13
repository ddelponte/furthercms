package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ModuleService)
@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, PrimaryAdminMenuItem, SecondaryAdminMenuItem, ModuleType, Module, HtmlModule])
class ModuleServiceSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    def "test module creation"() {
        given:
        initAllData()
        def moduleTypeInstance = ModuleType.findByName(moduleTypeName)
        def pageInstance = Page.findByTitle(pageInstanceTitle)
        assert pageInstance?.modules?.size() == moduleCountBefore

        when:
        def newModule = service.create(moduleTypeInstance, pageInstance)

        then:
        newModule?.displayOrder == displayOrder
        pageInstance?.modules?.size() == moduleCountAfter

        where:
        moduleTypeName | pageInstanceTitle  | moduleCountBefore | moduleCountAfter | displayOrder
        "HTML"         | htmlChildPageTitle | 1                 | 2                | 2
        "Invalid"      | htmlChildPageTitle | 1                 | 1                | null
        "HTML"         | "Invalid"          | null              | null             | null
        "Invalid"      | "Invalid"          | null              | null             | null
    }
}
