package com.merrycoders.furthercms

import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import com.merrycoders.furthercms.bootstrap.PageTypeBootstrap
import com.merrycoders.furthercms.modules.Module
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PageTypeController)
@Mock([PageType, PageTypeModuleType, Page, ModuleType, Module, Category, PrimaryCategory, PageTypeModuleType])
class PageTypeControllerSpec extends SpecificationDataCore {

    def setup() {
        controller.pageTypeService = new PageTypeService()
        controller.pageTypeModuleTypeService = new PageTypeModuleTypeService()
        controller.metaClass.displayFlashMessage = { LinkedHashMap args -> return args.text }
    }

    def cleanup() {}

    def "undeletable PageType"() {
        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        def originalPageTypeCount = PageType.count()

        when:
        controller.delete(pageType.id)

        then:
        response.redirectedUrl == "/pageType/edit/${pageType.id}"
        PageType.count() == originalPageTypeCount

    }

    def "delete"() {
        given:
        initCategories()
        def originalPageTypeCount = PageType.count()
        def pageTypeToDelete = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        new PageTypeBootstrap().deleteAllPageReferences(pageTypeToDelete)

        when:
        controller.delete(pageTypeToDelete.id)

        then:
        response.redirectedUrl == "/pageType/list"
        PageType.count() == originalPageTypeCount - 1

    }

    def "edit"() {
        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)

        when:
        controller.edit(pageType.id)

        then:
        response.forwardedUrl == "/grails/admin/index.dispatch"

    }

    def "updateModuleTypes"() {

        given:
        initCategories()
        def pageType = PageType.findByPageTypeKey(com.merrycoders.furthercms.bootstrap.PageType.HTML.pageTypeKey)
        PageTypeModuleTypeStatus.values().eachWithIndex { pageTypeModuleTypeStatus, index ->
            5.times {
                def moduleType = new ModuleType(name: "Module Type $it $index", className: "bogus.class$it$index", code: "bogus.code", )
                CoreBootstrap.saveDomainObjects([moduleType])
                PageTypeModuleType.create(pageType, moduleType, pageTypeModuleTypeStatus)
            }

        }

        def activeModuleTypes
        def availableModuleTypes
        def unavailableModuleTypes

        PageTypeModuleTypeStatus.values().each { pageTypeModuleTypeStatus ->
            def pageTypeModuleTypes = PageTypeModuleType.findAllByPageType(pageType)
            activeModuleTypes = pageTypeModuleTypes.findAll {it.status == PageTypeModuleTypeStatus.ACTIVE}?.moduleType
            availableModuleTypes = pageTypeModuleTypes.findAll {it.status == PageTypeModuleTypeStatus.AVAILABLE}?.moduleType
            unavailableModuleTypes = pageTypeModuleTypes.findAll {it.status == PageTypeModuleTypeStatus.UNAVAILABLE}?.moduleType
            controller.params[pageTypeModuleTypeStatus.name] = ""
        }

        def activeJsonString = buildJsonString(activeModuleTypes, PageTypeModuleTypeStatus.ACTIVE.name)
        def availableJsonString = buildJsonString(availableModuleTypes, PageTypeModuleTypeStatus.AVAILABLE.name)
        def unavailableJsonString = buildJsonString(unavailableModuleTypes, PageTypeModuleTypeStatus.UNAVAILABLE.name)

        def originalActiveCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        def originalAvailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        def originalUnavailableCount = PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

        controller.params["Active"] = activeJsonString
        controller.params["Available"] = availableJsonString
        controller.params["Unavailable"] = unavailableJsonString

        when:
        controller.updateModuleTypes(pageType.id)

        then:
        originalActiveCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.ACTIVE)
        originalAvailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.AVAILABLE)
        originalUnavailableCount == PageTypeModuleType.countByPageTypeAndStatus(pageType, PageTypeModuleTypeStatus.UNAVAILABLE)

    }

    /**
     *
     * @param moduleTypes
     * @param statusString
     * @return A JSON String representing the hidden field on the form
     */
    private String buildJsonString(List<ModuleType> moduleTypes, String statusString) {

        def jsonString = "{"

        moduleTypes.eachWithIndex {moduleType, index ->
            def comma = index > 0 ? "," : ""
            jsonString += "${comma}\"${moduleType.id}\":\"${statusString}\""
        }

        jsonString += "}"

        return jsonString

    }
}