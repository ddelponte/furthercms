import com.merrycoders.furthercms.bootstrap.CoreBootstrap
import grails.util.Environment

class FurtherCmsBootStrap {

    def init = { servletContext ->
        if (Environment.isDevelopmentMode() || Environment.current == Environment.TEST) {
            CoreBootstrap.initDevData()
        } else {
            CoreBootstrap.initProductionData()
        }
    }

    def destroy = {}

}