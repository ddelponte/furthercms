import com.merrycoders.furthercms.bootstrap.Core
import grails.util.Environment

class FurtherCmsBootStrap {

    def init = { servletContext ->
        if (Environment.isDevelopmentMode() || Environment.current == Environment.TEST) {
            Core.initDevData()
        } else {
            Core.initProductionData()
        }
    }

    def destroy = {}

}