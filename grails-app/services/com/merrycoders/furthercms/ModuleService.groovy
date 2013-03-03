package com.merrycoders.furthercms

import org.apache.commons.lang.StringUtils

class ModuleService {

    List<Module> convertFormInputToModules(Page pageInstance, Map<String, String> moduleStrings) {

        moduleStrings.each { k, v ->
            Long id = StringUtils.substringBetween(k, "id_", "_version")?.toLong()
            Long version = StringUtils.substringBetween(k, "version_", "_moduleDataId")?.toLong()
            String dataKey = StringUtils.substringBetween(k, "dataKey_", "_displayOrder")
            Long displayOrder = StringUtils.substringAfterLast(k, "_")?.toLong()
            String dataValue = v
        }

    }
}
