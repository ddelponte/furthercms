package com.merrycoders.furthercms

class HomeController {

    def index() {
        forward(controller: "requestDispatch", action: "dispatch", params: [path: ""])
    }
}
