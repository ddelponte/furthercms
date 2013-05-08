package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(AdminController)
@Mock([Category, Page, PageType, Module, ModuleType, PageTypeModuleType, PrimaryCategory, PrimaryAdminMenuItem, SecondaryAdminMenuItem])
class AdminControllerSpec extends SpecificationDataCore {

    def setup() {}

    def "index"() {
        given:
        initNavAdminMenuItems()
        initCategories()
        request.chainModel = [params: [controller: "pageType", action: "list", max: "100"]]

        when:
        controller.index()

        then:
        view == "/admin/index"
        model.activePrimaryAdminMenuItem.titleDefault == "Home"
        model.activeSecondaryAdminMenuItem.titleDefault == "Page Types"
        model.contentTemplatePath == "/admin/contentTemplates/pages/noPage"
        model.params.size() == 3

    }

    def "edit"() {
        given:
        initNavAdminMenuItems()
        initCategories()
        def category = Category.findByName(categoryName)

        when:
        controller.edit(category.id)

        then:
        view == "/admin/index"
        model.activePrimaryAdminMenuItem.titleDefault == "Home"
        model.activeSecondaryAdminMenuItem.titleDefault == "Pages"
        model.categoryInstance == category
        model.categoryInstance.page == category.page
        model.pageType == PageType.findByPageTypeKey(pageTypeKey)
        model.modules.size() == 1
        model.contentTemplatePath == contentTemplatePath

        where:
        categoryName | pageTypeKey | contentTemplatePath
        "Site"       | "root"      | "/admin/contentTemplates/pages/noPage"
        "Home"       | "home"      | "/admin/contentTemplates/pages/edit"
        "HTML"       | "HTML"      | "/admin/contentTemplates/pages/edit"
        "HTML Child" | "HTML"      | "/admin/contentTemplates/pages/edit"

    }

}
