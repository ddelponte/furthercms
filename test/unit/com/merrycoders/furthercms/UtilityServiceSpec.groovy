package com.merrycoders.furthercms

import grails.test.mixin.TestFor

@TestFor(UtilityService)
class UtilityServiceSpec extends SpecificationDataCore {

    def setup() {}

    def "toSlug"() {
        when:
        def results = service.toSlug(pathFragment)

        then:
        results == expectedResults

        where:
        pathFragment | expectedResults
        "Further CMS"                  | "further-cms"
        "Further---CMS---Rocks"        | "further---cms---rocks"
        "Further CMS will make you \$" | "further-cms-will-make-you-"
    }
}
