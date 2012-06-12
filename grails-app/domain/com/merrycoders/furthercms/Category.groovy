package com.merrycoders.furthercms

class Category {
    Category parent
    String urlKey
    Page page

    static constraints = {
        parent(nullable: true)
        page(nullable: true)
    }
}
