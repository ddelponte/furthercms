package com.merrycoders.furthercms.modules

import com.merrycoders.furthercms.ModuleType
import com.merrycoders.furthercms.Page

class Module {
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
        tablePerHierarchy false
    }

    /**
     * Creates and persists a Module instance of the appropriate type. The type of Module persisted is determined by the Module's ModuleType class name.
     * @param data A map of the form [moduleType: moduleTypeInstance, page: pageInstance, flush: boolean, ... specific module data]
     * @return the Module instance
     */
    static Module create(Map data) {
        ModuleType moduleType = data.moduleType
        Page page = data.page
        def flush = data.flush ?: false
        Integer displayOrder = Module.findAllByPage(page)?.displayOrder?.max() ?: 0
        displayOrder++
        data.displayOrder = displayOrder
        def module = Class.forName(moduleType.className, true, Thread.currentThread().contextClassLoader).newInstance()
        data.each { k, v ->
            if (module.metaClass.hasProperty(module, k)) {
                module."${k}" = v
            }
        }
        module.save(flush: flush, insert: true)

        return module
    }

    String toString() {
        "${moduleType}"
    }
}
