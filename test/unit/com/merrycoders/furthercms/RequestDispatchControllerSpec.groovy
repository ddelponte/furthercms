package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(RequestDispatchController)
@Mock([Category, PrimaryCategory, Page, PageType, PrimaryAdminMenuItem, SecondaryAdminMenuItem, ModuleType, Module, ModuleData])
class RequestDispatchControllerSpec extends SpecificationDataCore {
    def setup() {
        controller.categoryService = new CategoryService()
    }

    def "PageType dispatching"() {
        given:
        initAllData()
        params["path"] = path

        when:
        controller.dispatch()

        then:
        view == expectedView
        model.toString() == expectedModel

        where:
        path                   | expectedView                   | expectedModel
        "home"                 | "/homePageType/renderPage.gsp" | "[:]"
        "home/html"            | "/public/sidebar"              | "[categoryInstance:HTML, pageInstance:HTML Title, modules:[HTML]]"
        "home/html/html-child" | "/public/sidebar"              | "[categoryInstance:HTML Child, pageInstance:HTML Child Title, modules:[HTML]]"

    }
}
