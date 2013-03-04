package com.merrycoders.furthercms

import org.apache.commons.lang.StringUtils

class ModuleService {

    /**
     *
     * @param pageInstance
     * @param moduleStrings ["id_3_version_0_displayOrder_1_moduleTypeId_1_moduleDataId_3_moduleDataVersion_0_dataKey_html", "<p>some html</p>"]
     * @return List of Module instances
     */
    List<Module> convertFormInputToModules(Page pageInstance, Map<String, String> moduleStrings) {
        def moduleInstanceList = []

        moduleStrings.each { k, v ->
            Long moduleTypeId = StringUtils.substringBetween(k, "moduleTypeId_", "_moduleDataId")?.toLong()
            ModuleType moduleType = ModuleType.get(moduleTypeId)

            def moduleProperties = [
                    id: StringUtils.substringBetween(k, "id_", "_version")?.toLong(),
                    version: StringUtils.substringBetween(k, "version_", "_displayOrder")?.toLong(),
                    displayOrder: StringUtils.substringBetween(k, "displayOrder_", "_moduleTypeId")?.toLong(),
                    moduleType: moduleType,
                    page: pageInstance
            ]

            def module = moduleProperties.id ? Module.get(moduleProperties.id) : new Module()
            module.properties = moduleProperties
            module.save(flush: true)


            def moduleDataProperties = [
                    id: StringUtils.substringBetween(k, "moduleDataId_", "_moduleDataVersion")?.toLong(),
                    version: StringUtils.substringBetween(k, "moduleDataVersion_", "_dataKey")?.toLong(),
                    dataKey: StringUtils.substringAfterLast(k, "_"),
                    dataValue: v
            ]

            def moduleData = moduleDataProperties.id ? ModuleData.get(moduleDataProperties.id) : new ModuleData()
            moduleData.properties = moduleDataProperties
            moduleData.save(flush: true)

            moduleInstanceList << module

        }

        return moduleInstanceList

    }
}
