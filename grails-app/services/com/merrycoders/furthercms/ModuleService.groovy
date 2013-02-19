package com.merrycoders.furthercms

import grails.gsp.PageRenderer

class ModuleService {
    PageRenderer groovyPageRenderer

    def renderPublicView(Module module) {

        return groovyPageRenderer.render(template: "/modules/html/public", model: [data: module?.moduleData])

    }
}
