package com.merrycoders.furthercms

class ModuleData {
    Module module
    String dataKey
    String dataValue
    Date dateCreated
    Date lastUpdated

    static constraints = {
        dataValue nullable: true
    }

    static mapping = {
        cache true
        module fetch: 'join'
    }

    String toString() {
        "${dataKey}: ${dataValue}"
    }
}
