package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import grails.validation.ValidationException

class PageService {
    def moduleService

    Page save(Page page, Boolean flush = false) throws ValidationException {
        if (!page) return null

        if (!page.validate()) {
            throw new ValidationException("Page is not valid", page.errors)
        }

        page.save(flush: flush)
        return page
    }

    /**
     * Delete the page if it does not belong to more than one category.  Also delete all of the Page's Module instances
     * @param page
     */
    void delete(Page page) {
        if (page) {

            def modules = Module.findAllByPage(page)
            moduleService.deleteModules(modules)

            if (Category.countByPage(page) <= 1) {
                page.delete(flush: true)
            }

        }
    }

}
