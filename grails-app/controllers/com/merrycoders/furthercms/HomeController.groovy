package com.merrycoders.furthercms

class HomeController {

    def index() {
        def categoryInstance = Category.findByUrlKey("home")
        render(view: "/public/home/index", model: [categoryInstance: categoryInstance])
    }
}
