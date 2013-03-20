package com.merrycoders.furthercms

import com.merrycoders.furthercms.modules.Module
import org.apache.commons.lang.StringUtils

class ModuleType {
    String name
    String className
    String description = ""
    String code // i18n code representing the name
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name unique: true
        className unique: true, maxSize: 1000
        description maxSize: 30000
    }

    static mapping = {
        cache: true
        index: 'Name_Idx'
    }

    /**
     * Returns the unique view folder name for modules of this type.  The folder name is determined by the simple class name.
     * @return
     */
    String getViewFolder() {
        def simpleClassName = StringUtils.substringAfterLast(className, ".")
        def viewFolder = StringUtils.substringBeforeLast(simpleClassName, "Module")?.toLowerCase()
        return viewFolder
    }

    /**
     * Returns a new instance of the Module associated with this ModuleType
     * @return Module
     */
    Module getModule() {
        if (!this?.id) return null
        def module = Class.forName(this.className, true, Thread.currentThread().contextClassLoader).newInstance()
        module.moduleType = this
        return module
    }

    String toString() {
        name
    }
}
