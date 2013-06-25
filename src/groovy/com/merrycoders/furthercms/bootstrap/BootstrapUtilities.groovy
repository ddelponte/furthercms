package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.PageTypeModuleTypeStatus

/**
 * Cool and wonderful things to assist with Bootstrap activities
 */
class BootstrapUtilities {

    /**
     *
     * @param moduleTypes List of ModuleType instances
     * @param pageTypeModuleTypeStatus
     * @return A JSON String representing the hidden field on the form
     */
    static String buildModuleTypeJsonString(List<com.merrycoders.furthercms.ModuleType> moduleTypes, PageTypeModuleTypeStatus status) {

        def statusString = status?.name

        def jsonString = "{"

        moduleTypes.eachWithIndex { moduleType, index ->
            def comma = index > 0 ? "," : ""
            jsonString += "${comma}\"${moduleType.id}\":\"${statusString}\""
        }

        jsonString += "}"

        return jsonString

    }

}
