package com.merrycoders.furthercms

import com.merrycoders.furthercms.exceptions.UndeletablePageTypeException
import org.springframework.dao.DataIntegrityViolationException

class PageTypeService {

    /**
     * Deletes the PageType instance, objects dependent on it and references to this instance.
     * A PageType may only be deleted if no Page instances reference it.
     * @param pageTypeInstance
     */
    void delete(PageType pageTypeInstance) throws DataIntegrityViolationException, UndeletablePageTypeException {

        if (isDeletable(pageTypeInstance)) {

            PageTypeModuleType.findAllByPageType(pageTypeInstance)*.delete(flush: true)
            pageTypeInstance.delete(flush: true)

        } else {
            throw new UndeletablePageTypeException(pageType: pageTypeInstance)
        }

    }

    /**
     * Checks to see if the PageType instance is deletable
     *
     * @param pageTypeInstance
     * @return True if this PageType instance may be deleted, else false
     */
    private Boolean isDeletable(PageType pageTypeInstance) {

        if (pageTypeInstance && !Page.countByPageType(pageTypeInstance)) return true
        else return false

    }

    /**
     * Returns a map of ModuleTypes for the specified PageType, organized by status and properly ordered
     * @param pageType
     * @return a Map in form [PageTypeModuleTypeStatus.ACTIVE, [ModuleType1, ModuleType2, ...]]
     */
    Map<PageTypeModuleTypeStatus, ModuleType> findAllModuleTypesByPageType(PageType pageType) {
        if (!pageType) return [:]
        def statusModuleTypes = [:]

        PageTypeModuleTypeStatus.values().sort { it.name }.each { status ->
            def params = [:]

            if (status == PageTypeModuleTypeStatus.ACTIVE) {

                params = [sort: "displayOrder", order: "asc"]

            } else {

                params = [sort: "moduleType.name", order: "asc"]

            }

            def moduleTypes = PageTypeModuleType.findAllByPageTypeAndStatus(pageType, status, params)?.moduleType
            statusModuleTypes[status] = moduleTypes
        }

        return statusModuleTypes

    }
}
