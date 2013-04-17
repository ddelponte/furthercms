package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.plugin.spock.IntegrationSpec

class CategoryControllerIntegrationSpec extends IntegrationSpec {
    def setup() {}

    def "delete"() {
        given:
        def page = Page.findByTitle(pageTitle)
        def category = Category.findByPage(page)
        def initialCategoryCount = Category.count()
        def initialPageCount = Page.count()
        def initialModuleCount = Module.count()
        def initialPrimaryCategoryCount = PrimaryCategory.count()

        when:
        new CategoryController().delete(category.id)

        then:
        Category.count() == initialCategoryCount - deletedCategories
        Page.count() == initialPageCount - deletedPages
        Module.count() == initialModuleCount - deletedModules
        PrimaryCategory.count() == initialPrimaryCategoryCount - deletedPrimaryCategories

        where:
        pageTitle          | deletedCategories | deletedPages | deletedModules | deletedPrimaryCategories
        "HTML Child Title" | 1                 | 1            | 1              | 0
        "HTML Title"       | 2                 | 2            | 2              | 0
        "Home Title"       | 3                 | 3            | 3              | 1
    }

}
