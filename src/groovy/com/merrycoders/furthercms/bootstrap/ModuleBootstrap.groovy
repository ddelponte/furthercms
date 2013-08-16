package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.ModuleService
import com.merrycoders.furthercms.Page
import com.merrycoders.furthercms.PageTypeModuleType
import com.merrycoders.furthercms.PageTypeModuleTypeStatus
import com.merrycoders.furthercms.modules.HtmlModule

class ModuleBootstrap {

    static init(List<Page> pages) {
        if (!com.merrycoders.furthercms.ModuleType.count()) ModuleTypeBootstrap.init()

        pages.each { page ->

            def moduleType = com.merrycoders.furthercms.ModuleType.findByClassName(HtmlModule.class.name)
            def module = new ModuleService().create([moduleType: moduleType, page: page, html: "<p>Where are we going?</p>", flush: true])
            PageTypeModuleType.create(page.pageType, module.moduleType, PageTypeModuleTypeStatus.ACTIVE)

        }
    }

}
