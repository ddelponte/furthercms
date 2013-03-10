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
        resource(url: "js/jquery.form.js")
    }

    modulesSave {
        dependsOn "jquery, form"
        resource(url: "js/admin/modulesSave.js")
    }

    hotKeys {
        dependsOn "jquery"
        resource(url: "js/jquery.hotkeys.js")
    }
}