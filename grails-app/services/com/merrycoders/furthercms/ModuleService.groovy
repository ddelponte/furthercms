package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module

class ModuleService {

    /**
     *
     * @param moduleTypeInstance
     * @param pageInstance
     * @return Module instance
     */
    def create(ModuleType moduleTypeInstance, Page pageInstance) {
        if (!moduleTypeInstance || !pageInstance) return null

        def module = Module.create([moduleType: moduleTypeInstance, page: pageInstance, html: "", flush: true])
        module.page = pageInstance
        module.save(flush: true)

        return module
    }

    def delete(List<Long> ids) {
        ids.each { id ->
            def module = Module.get(id)
            module.delete(flush: true)
        }
    }
}
