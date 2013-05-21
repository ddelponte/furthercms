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
}
