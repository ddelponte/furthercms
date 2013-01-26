modules = {

    application {
        resource url: 'js/application.js'
    }

    jsTree {
        dependsOn "jquery"
        resource(url: "/js/jstree-v.pre1.0/jquery.jstree.js")
    }

    jqueryCookie {
        dependsOn "jquery"
        resource(url: "/js/navTree/jquery.cookie.js")
    }

    navTree {
        dependsOn "jsTree, jqueryCookie"
        resource(url: "/js/navTree/navTree.js")
    }
}