package com.merrycoders.furthercms

class Module {
    def moduleService

    ModuleType moduleType
    Page page
    Integer displayOrder
    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        cache true
        moduleType fetch: 'join'
    }

    def beforeDelete() {
        ModuleData.withNewSession { moduleData*.delete() }
    }

    def List getModuleData() {
        ModuleData.findAllByModule(this)
    }

    static Module create(Map data) {
        ModuleType moduleType = data.moduleType
        Page page = data.page
        def flush = data.flush ?: false
        Integer displayOrder = Module.findAllByPage(page)?.displayOrder?.max() ?: 0
        displayOrder++
        new Module(page: page, moduleType: moduleType, displayOrder: displayOrder).save(flush: flush, insert: true)
    }

    def renderModule() {
        moduleService.renderPublicView(this)
    }

    String toString() {
        "${moduleType}"
    }
}
