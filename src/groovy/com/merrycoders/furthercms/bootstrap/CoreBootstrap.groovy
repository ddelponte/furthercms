package com.merrycoders.furthercms.bootstrap

import com.merrycoders.furthercms.modules.HtmlModule

class CoreBootstrap {

    static rootCategoryName = "Site"
    static homeCategoryName = "Home"
    static htmlCategoryName = "HTML"
    static htmlChildCategoryName = "HTML Child"

    static rootPageTitle = "Site"
    static homePageTitle = "Home Title"
    static htmlPageTitle = "HTML Title"
    static htmlChildPageTitle = "HTML Child Title"

    static pageTypePropertyList = [
            [name: PageType.HTML.name, pageTypeKey: PageType.HTML.pageTypeKey],
            [name: PageType.HOME.name, pageTypeKey: PageType.HOME.pageTypeKey],
            [name: PageType.ROOT.name, pageTypeKey: PageType.ROOT.pageTypeKey, description: PageType.ROOT.description]]

    static moduleTypePropertyList = [
            [name: ModuleType.HTML.name, className: ModuleType.HTML.className, code: ModuleType.HTML.code]]

    public static saveDomainObjects(List domainObjects) {

        domainObjects.each { object ->
            if (!object.save(flush: true)) {
                println object.errors.fieldErrors
            }
        }
    }

    static initDevData() {
        CategoryBootstrap.init()
        NavAdminMenuItemsBootstrap.init()
    }

    static initProductionData() {
        ModuleTypeBootstrap.init()
        PageTypeBootstrap.init()
        NavAdminMenuItemsBootstrap.init()
    }

}

enum PageType {
    HOME("Home Page Type", "home"),
    HTML("HTML Page Type", "HTML"),
    ROOT("Root Page Type", "root", "Placeholder for a node representing the root node of the website")

    String name = ""
    String pageTypeKey = ""
    String description = ""

    PageType(String name, String pageTypeKey, String description = "") {
        this.name = name
        this.pageTypeKey = pageTypeKey
    }

    String toString() {
        return name
    }
}

enum ModuleType {
    HTML("HTML", HtmlModule.class.name, "com.merrycoders.furthercms.moduletype.html")

    String name = ""
    String className = ""
    String code = ""

    ModuleType(String name, String className, String code) {
        this.name = name
        this.className = className
        this.code = code
    }

    String toString() {
        return name
    }
}