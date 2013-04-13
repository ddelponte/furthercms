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

    /**
     *
     * @param ids List of Long Module ids
     */
    def void delete(List<Long> ids) {
        if (ids - null) {
            ids.each { id ->
                def module = Module.get(id)
                module.delete(flush: true)
            }
        }
    }
}
