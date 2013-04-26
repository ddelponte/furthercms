package com.merrycoders.furthercms.bootstrap

class Core {
    static rootPageTitle = "Site"
    static homePageTitle = "Home Title"
    static htmlPageTitle = "HTML Title"
    static htmlChildPageTitle = "HTML Child Title"

    public static saveDomainObjects(List domainObjects) {

        domainObjects.each { object ->
            if (!object.save(flush: true)) {
                println object.errors.fieldErrors
            }
        }
    }

}
