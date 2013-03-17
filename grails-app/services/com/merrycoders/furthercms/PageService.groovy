package com.merrycoders.furthercms

class PageService {

    public Page save(Page page, Boolean flush = false) {
        if (!page) return null
        page.save(flush: flush)
        return page
    }
}
