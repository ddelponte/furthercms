package com.merrycoders.furthercms.bootstrap

class ModuleTypeBootstrap {

    static init() {

        if (!com.merrycoders.furthercms.ModuleType.count()) {
            Core.moduleTypePropertyList.each { properties ->
                def moduleType = new com.merrycoders.furthercms.ModuleType()
                moduleType.properties = properties
                Core.saveDomainObjects([moduleType])
            }
        }
    }

}
