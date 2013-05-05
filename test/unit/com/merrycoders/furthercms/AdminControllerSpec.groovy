package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(AdminController)
@Mock([Category, Page, PageType, Module, ModuleType, PageTypeModuleType, PrimaryCategory, PrimaryAdminMenuItem, SecondaryAdminMenuItem])
class AdminControllerSpec extends SpecificationDataCore {

    def setup() {}

    def "edit"() {
        given:
        initNavAdminMenuItems()
        initCategories()
        def category = Category.findByName(CoreBootstrap.htmlCategoryName)

        when:
        controller.edit(category.id)

        then:
        view == "/admin/index"
        model.activePrimaryAdminMenuItem.titleDefault == "Home"
        model.activeSecondaryAdminMenuItem.titleDefault == "Pages"
        model.categoryInstance == category
        model.categoryInstance.page == category.page
        model.pageType == PageType.findByPageTypeKey("HTML")
        model.modules.size() == 1
        model.contentTemplatePath == "/admin/contentTemplates/pages/edit"

    }
}
