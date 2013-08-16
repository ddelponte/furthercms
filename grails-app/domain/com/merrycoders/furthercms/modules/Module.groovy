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

    String toString() {
        "${moduleType}"
    }
}
