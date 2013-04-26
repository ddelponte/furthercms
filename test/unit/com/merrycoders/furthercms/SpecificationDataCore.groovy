package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.CategoryBootstrap
import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import com.merrycoders.furthercms.bootstrap.NavAdminMenuItemsBootstrap
import spock.lang.Specification

/**
 * This acts as a central test data repository for all Specification tests.  The goal is to reduce code duplication.
 */
class SpecificationDataCore extends Specification {
    def categoryService
    def primaryCategoryService

    def setup() {
        categoryService = initCategoryService()
    }

    def initCategoryService() {
        categoryService = new CategoryService()
        def pageService = new PageService()
        pageService.moduleService = new ModuleService()
        categoryService.pageService = pageService
        categoryService.utilityService = new UtilityService()
        primaryCategoryService = new PrimaryCategoryService()
        categoryService.primaryCategoryService = primaryCategoryService
        return categoryService
    }

    def mockFurtherCmsTagLib() {

        def mockTagLib = mockFor(FurtherCmsTagLib)
        mockTagLib.demand.renderModuleEdit(1..999) { Map attrs, String body ->
            return [moduleEditTag: attrs.module.toString()]
        }

        return mockTagLib.createMock()

    }

    def initAllData() {
        CoreBootstrap.initDevData()
    }

    def initCategories() {
        CategoryBootstrap.init()
    }

    def initNavAdminMenuItems() {
        NavAdminMenuItemsBootstrap.init()
    }

}
