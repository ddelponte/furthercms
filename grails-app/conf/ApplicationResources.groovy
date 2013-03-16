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
        dependsOn "jquery, jsTree, jqueryCookie"
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
        resource(url: "js/admin/date.format.js")
    }
}