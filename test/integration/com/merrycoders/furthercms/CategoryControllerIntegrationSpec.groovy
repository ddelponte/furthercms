package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.plugin.spock.IntegrationSpec

class CategoryControllerIntegrationSpec extends IntegrationSpec {
    def setup() {}

    def "delete"() {
        given:
        def page = Page.findByTitle("HTML Child Title")
        def category = Category.findByPage(page)
        def initialCategoryCount = Category.count()
        def initialPageCount = Page.count()
        def initialModuleCount = Module.count()

        when:
        new CategoryController().delete(category.id)

        then:
        Category.count() == initialCategoryCount - 1
        Page.count() == initialPageCount - 1
        Module.count() == initialModuleCount - 1
    }

    //TODO: Add tests for deleting of children and edge cases
}
