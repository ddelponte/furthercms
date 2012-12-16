package com.merrycoders.furthercms

class FurtherCmsTagLib {
    static namespace = "fc"

    def primaryNav = { attrs, body ->
        out << render(template: "/public/navigation/primary", model: [:])
        // out << render(template: "/templates/tags/tagIcons", model: [tagsWithIcon: tagsWithIcon, tagsWithoutIcon: tagsWithoutIcon])
    }

    def secondaryNav = { attrs, body ->
        out << render(template: "/public/navigation/secondary", model: [:])
    }

}
