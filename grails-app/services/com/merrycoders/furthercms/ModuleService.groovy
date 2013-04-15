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
     * @param modules List of Module instances
     */
    void deleteModules(List<Module> modules) {
        modules.each { module ->
            module.delete(flush: true)
        }
    }

    /**
     *
     * @param ids List of Long Module ids
     */
    void delete(List<Long> ids) {
        if (ids - null) {
            ids.each { id ->
                def module = Module.get(id)
                module.page = null
                module.delete(flush: true)
            }
        }
    }
}
