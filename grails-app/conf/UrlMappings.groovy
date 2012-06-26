class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')

        // Pretty URLs
        name prettyUrl: "/$path**?" {
            controller = "requestDispatch"
            action = "dispatch"
        }

        // Authentication
        "/login/$action"(controller: "login")
        "/logout/"(controller: "logout", action: "index")

        // Admin
        "/admin"(controller: "admin", action: "index")
        /*Convention*/
        "/admin/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }
    }
}
