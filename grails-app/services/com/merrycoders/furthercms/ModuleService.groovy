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
        def module = Module.create([moduleType: moduleTypeInstance, page: pageInstance, html: "", flush: true])
        module.page = pageInstance
        module.save(flush: true)

        return module
    }
}
