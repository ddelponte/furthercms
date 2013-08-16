package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module

class ModuleService {


    /**
     * Creates and persists a Module instance of the appropriate type. The type of Module persisted is determined by the Module's ModuleType class name.
     * @param data A map of the form [moduleType: moduleTypeInstance, page: pageInstance, flush: boolean, ... specific module data]
     * @return the Module instance
     */
    static Module create(Map data) {
        ModuleType moduleType = data?.moduleType
        Page page = data?.page
        def flush = data?.flush ?: false
        if (!data || !moduleType || !page) return null

        Integer displayOrder = Module.findAllByPage(page)?.displayOrder?.max() ?: 0
        displayOrder++
        data.displayOrder = displayOrder
        def module = moduleType.module
        data.each { k, v ->
            if (module.metaClass.hasProperty(module, k)) {
                module."${k}" = v
            }
        }

        module.page = page
        module.save(flush: flush)

        return module
    }

    /**
     *
     * @param modules List of Module instances
     */
    void deleteModules(List<Module> modules, Boolean flush = false) {
        modules.each { module ->
            module.delete(flush: flush)
        }
    }

    /**
     *
     * @param ids List of Long Module ids
     */
    void delete(List<Long> ids, Boolean flush = true) {
        if (ids - null) {
            ids.each { id ->
                def module = Module.get(id)
                module.page = null
                module.delete(flush: flush)
            }
        }
    }
}
