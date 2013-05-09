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
        request.chainModel = chainModel

        when:
        controller.index()

        then:
        view == "/admin/index"
        model.activePrimaryAdminMenuItem.titleDefault == primaryMenu
        model.activeSecondaryAdminMenuItem.titleDefault == secondaryMenu
        model.contentTemplatePath == contentTemplatePath
        model.params?.size() == paramsSize

        where:
        chainModel                                                     | primaryMenu | secondaryMenu | contentTemplatePath                         | paramsSize
        [params: [controller: "pageType", action: "list", max: "100"]] | "Home"      | "Page Types"  | "/admin/primaryAdminMenuItems/pages/noPage" | 3
        null                                                           | "Home"      | "Pages"       | "/admin/primaryAdminMenuItems/pages/noPage" | null
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
        "Site"       | "root"      | "/admin/primaryAdminMenuItems/pages/noPage"
        "Home"       | "home"      | "/admin/primaryAdminMenuItems/pages/edit"
        "HTML"       | "HTML"      | "/admin/primaryAdminMenuItems/pages/edit"
        "HTML Child" | "HTML"      | "/admin/primaryAdminMenuItems/pages/edit"

    }

}
