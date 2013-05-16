modules = {

    application {
        resource url: 'js/application.js'
    }

    jsTree {
        dependsOn "jquery"
        resource(url: "js/admin/jstree-v.pre1.0/jquery.jstree.js")
    }

    jqueryCookie {
        dependsOn "jquery"
        resource(url: "js/admin/navTree/jquery.cookie.js")
    }

    navTree {
        dependsOn "jquery, jsTree, jqueryCookie, json2"
        resource(url: "js/admin/navTree/navTree.js")
        resource(url: "css/admin/style.css")
    }

    urlUtilities {
        dependsOn "jquery"
        resource(url: "js/admin/urlUtilities.js")
    }

    form {
        dependsOn "jquery"
        resource(url: "js/admin/jquery.form.js")
    }

    modules {
        dependsOn "jquery, form"
        resource(url: "js/admin/modules.js")
    }

    hotKeys {
        dependsOn "jquery"
        resource(url: "js/admin/jquery.hotkeys.js")
    }

    changed {
        dependsOn "jquery"
        resource(url: "js/admin/jquery.changed.min.js")
    }

    categoryEditor {
        dependsOn "jquery,  changed"
        resource(url: "js/admin/modules/category/editor.js")
        resource(url: "js/admin/urlUtilities.js")
    }

    dateFormatter {
        dependsOn "jquery"
        resource(url: "js/admin/date.format.js")
    }

    slidePanel {
        dependsOn "jquery"
        resource(url: "js/admin/slide.panel/css/jquery.slidepanel.css")
        resource(url: "js/admin/slide.panel/js/jquery.slidepanel.js")
        resource(url: "js/admin/slide.panel/load.js")
    }

    moduleTypes {
        dependsOn "jquery"
        resource(url: "js/admin/moduleTypes.js")
    }

    reorderModules {
        dependsOn "jquery, jquery-ui, modules"
        resource(url: "js/admin/components/reorderModulesButton/reorderModules.js")
    }

    json2 {
        resource(url: "js/admin/json2.js")
    }

    deleteModuleIcon {
        dependsOn "jquery"
        resource(url: "js/admin/components/deleteModuleIcon/deleteModuleIcon.css")
        resource(url: "js/admin/components/deleteModuleIcon/deleteModuleIcon.js")
    }

    newCategoryButton {
        dependsOn "jquery"
        resource(url: "js/admin/components/newCategoryButton/newCategoryButton.css")
        resource(url: "js/admin/components/newCategoryButton/newCategoryButton.js")
    }

    pageTypeForm {
        dependsOn "jquery"
        resource(url: "js/admin/primaryAdminMenuItems/pageTypes/core.js")
    }
}