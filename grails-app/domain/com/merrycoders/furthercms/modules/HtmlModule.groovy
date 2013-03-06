package com.merrycoders.furthercms.modules

class HtmlModule extends Module {
    String html
    Date dateCreated
    Date lastUpdated

    static constraints = {
        html nullable: true, maxSize: 1000000
    }
}
