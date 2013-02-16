package com.merrycoders.furthercms

class ModuleType {
    String name
    String moduleTypeKey
    String description = ""
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name unique: true
        moduleTypeKey unique: true
        description maxSize: 30000
    }

    static mapping = {
        cache: true
        index: 'Name_Idx'
        moduleTypeKey: "ModuleTypeKey_Idx"
    }

    String toString() {
        name
    }
}
