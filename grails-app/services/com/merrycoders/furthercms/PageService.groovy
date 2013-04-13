package com.merrycoders.furthercms

class PageService {

    def Page save(Page page, Boolean flush = false) {
        if (!page) return null
        page.save(flush: flush)
        return page
    }

    def delete(Page page) {
        if (page) page.delete(flush: true)
    }

}
