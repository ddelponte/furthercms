import grails.util.Environment

class UrlMappings {

    static mappings = {
        "/"(controller: "homePageType", action: "renderPage") {}

        // Errors
        //"403"(controller: "errorPage", action: "forbidden")
        //"404"(controller: "errorPage", action: "notFound")

        if (Environment.DEVELOPMENT) {
            "500"(view: '/error')
        } else {
            "403"(controller: 'error', action: 'index', params: ['code': '403'])
            "404"(controller: 'error', action: 'index', params: ['code': '404'])
            "500"(controller: 'error', action: 'index', params: ['code': '500'])
        }

        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        // Pretty URLs
        name prettyUrl: "/$path**?" {
            controller = "requestDispatch"
            action = "dispatch"
        }

        // Admin
        "/admin"(controller: "admin", action: "index")
        "/admin/index"(controller: "admin", action: "index")

        /*Convention*/
        "/admin/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        // Authentication
        "/login/$action"(controller: "login")
        "/logout/"(controller: "logout", action: "index")

        // Public
        //"/sitemap"(controller: "sitemap", action: "index")
    }
}
