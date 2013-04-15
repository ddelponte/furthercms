package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.HtmlModule
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(FurtherCmsTagLib)
@Mock([Category, PrimaryCategory, Page, PageType, PageTypeModuleType, PrimaryAdminMenuItem, SecondaryAdminMenuItem, ModuleType, Module, HtmlModule])
class FurtherCmsTagLibSpec extends SpecificationDataCore {

    def setup() {
    }

    def cleanup() {
    }

    def "primary nav construction"() {
        given:
        initCategories()
        def categoryInstance = Category.findByName("HTML Child")

        when:
        def results = tagLib.primaryNav([categoryInstance: categoryInstance])

        then:
        assert results.contains("<li class=\"active\">")
        assert results.contains("<nav:title item=\"{titleMessageCode=com.merrycoders.furthercms.category, titleDefault=Home}")
    }

    def "secondary nav construction"() {
        given:
        initCategories()
        def leafCategory = Category.findByName(activeCategoryName)

        when:
        def results = tagLib.secondaryNav([categoryInstance: leafCategory])

        then:
        assert results.contains("class=\"nav nav-pills\"")
        assert results.contains("<li class=\"active\">")
        assert results.contains('<p:callTag tag="g:link" attrs="{url=/furthercms/home-title/html-title}">')
        assert results.contains("<nav:title item=\"{titleMessageCode=com.merrycoders.furthercms.category, titleDefault=HTML}\"></nav:title>")

        where:
        activeCategoryName << ["HTML Child", "HTML"]
    }

    def "admin primary nav construction"() {
        given:
        initNavAdminMenuItems()
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByTitleDefault("Home")

        when:
        def results = tagLib.primaryNavAdmin([activePrimaryAdminMenuItem: activePrimaryAdminMenuItem])

        then:
        assert results.contains("<li class=\"active\">")
        assert results.contains("<p:callTag tag=\"g:link\" attrs=\"{url=/furthercms/admin/index}\">")
        assert results.contains("<nav:title item=\"{titleMessageCode=furthercms.admin.primary.navigation.home, titleDefault=Home}\"></nav:title>")
    }

    def "admin secondary nav construction"() {
        given:
        initNavAdminMenuItems()
        def activePrimaryAdminMenuItem = PrimaryAdminMenuItem.findByTitleDefault("Home")
        def activeSecondaryAdminMenuItem = SecondaryAdminMenuItem.findByTitleDefault("Pages")
        if (!multipleSecondaryAdminMenuItem) SecondaryAdminMenuItem.list().last().delete()
        def liTag = '<li class="active">'
        def pTag = '<p:callTag tag="g:link" attrs="{url=/furthercms/admin/pages}">'
        def navTag = '<nav:title item="{titleMessageCode=furthercms.admin.primary.navigation.pages, titleDefault=Pages}"></nav:title>'

        when:
        def results = tagLib.secondaryNavAdmin([activePrimaryAdminMenuItem: activePrimaryAdminMenuItem, activeSecondaryAdminMenuItem: activeSecondaryAdminMenuItem])

        then:
        if (multipleSecondaryAdminMenuItem) {
            assert results.contains(liTag)
            assert results.contains(pTag)
            assert results.contains(navTag)
        } else {
            assert !results.contains(liTag)
            assert !results.contains(pTag)
            assert !results.contains(navTag)
        }

        where:
        multipleSecondaryAdminMenuItem << [false, true]
    }

    def "navTree construction"() {
        given:
        initCategories()
        def rootCategory = Category.findByUrlKey("")

        when:
        def results = tagLib.navTree([category: rootCategory])

        then:
        assert results.contains("<li id=\"category_2\">")
        assert results.contains("<a href=\"/admin/edit/2\" class=\"\">Home</a>")
        assert results.contains("<li id=\"category_3\">")
        assert results.contains("<a href=\"/admin/edit/3\" class=\"\">HTML</a>")
        assert results.contains("<li id=\"category_4\">")
        assert results.contains("<a href=\"/admin/edit/4\" class=\"\">HTML Child</a>")

    }

    def "renderPublicModule"() {
        given:
        initAllData()
        def page = Page.findByTitle(htmlPageTitle)
        def modules = page.modules
        def module = modules.first()
        assert modules.size() == 1

        when:
        def results = tagLib.renderModule([module: module])

        then:
        assert results.contains("<p>Where are we going?</p>")
    }

    def "renderModuleEdit"() {
        given:
        initAllData()
        def page = Page.findByTitle(htmlPageTitle)
        def modules = page.modules
        def module = modules.first()
        assert modules.size() == 1

        when:
        def results = tagLib.renderModuleEdit([module: module])

        then:
        assert results.contains('<ckeditor:editor name="html')
        assert results.contains('" height="100%" width="100%" toolbar="Mytoolbar">')
    }

    def "htmlEditor"() {
        given:
        initAllData()
        def data = [dataValue: "<p>HTML data</p>"]

        when:
        def results = tagLib.htmlEditor([name: "Name", data: data, height: "50%", width: "50%"])

        then:
        assert results.contains('<ckeditor:editor name="Name')
        assert results.contains('" height="50%" width="50%" toolbar="Mytoolbar">')
        assert results.contains('<p>HTML data</p>')
    }

    def "categoryEditor"() {
        given:
        initAllData()
        def page = Page.findByTitle(htmlChildPageTitle)
        def category = Category.findByPage(page)
        category.metaClass.pageTitleToSlug = {-> return "slug" }

        when:
        def results = tagLib.categoryEditor([category: category, page: page])

        then:
        assert results.contains('<r:require modules="categoryEditor"></r:require>')
        assert results.contains('<ui:form controller="category" action="update">')
        assert results.contains('<input type="hidden" name="category.id" value="4" id="category.id" />')
        assert results.contains('<input type="hidden" name="category.version" value="0" id="category.version" />')
        assert results.contains('<input type="hidden" name="page.id" value="3" id="page.id" />')
        assert results.contains('<input type="hidden" name="page.version" value="1" id="page.version" />')
        assert results.contains('<input type="hidden" name="modulesToDelete" value="{}" id="modulesToDelete" />')
        assert results.contains('<ui:field name="page.title" type="text" label="category.page.title.label" value="HTML Child Title"></ui:field>')
        assert results.contains('<div class="plugin.furthercms.category.urlkey.label">home-title/html-title/<span>slug</span></div>')
    }
}