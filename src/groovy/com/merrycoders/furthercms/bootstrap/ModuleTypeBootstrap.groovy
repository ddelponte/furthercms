package com.merrycoders.furthercms.bootstrap

class ModuleTypeBootstrap {

    static init() {

        if (!com.merrycoders.furthercms.ModuleType.count()) {
            CoreBootstrap.moduleTypePropertyList.each { properties ->
                def moduleType = new com.merrycoders.furthercms.ModuleType()
                moduleType.properties = properties
                CoreBootstrap.saveDomainObjects([moduleType])
            }
        }
    }

}
