package com.merrycoders.furthercms

class ModuleType {
    String name
    String className
    String description = ""
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
        moduleTypeKey: "ModuleTypeKey_Idx"
    }

    String toString() {
        name
    }
}
