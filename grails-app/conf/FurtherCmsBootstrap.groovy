import com.merrycoders.furthercms.PageType

class FurtherCmsBootStrap {

    def init = { servletContext ->
        initPageTypes()
    }

    def destroy = {
    }

    def initPageTypes() {
        def pageTypePropertyList = [
                [name: "HTML Page Type", controller: "htmlPageType", pageTypeKey: "HTML", action: "renderPage"]]

        pageTypePropertyList.each { properties ->
            if (!PageType.findByPageTypeKey(properties.pageTypeKey)) {
                def pageType = new PageType()
                pageType.properties = properties
                if (!pageType.save(flush: true)) {
                    log.error "Unable to save PageType ${pageType} due to errors: ${pageType?.errors?.fieldErrors}"
                }
            }
        }
    }
}