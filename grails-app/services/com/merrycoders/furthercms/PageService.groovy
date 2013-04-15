package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module

class PageService {
    def moduleService

    Page save(Page page, Boolean flush = false) {
        if (!page) return null
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
