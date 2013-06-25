grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.5'
        runtime 'mysql:mysql-connector-java:5.1.25'
    }

    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.0.4") {
            export = false
        }
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":jquery-ui:1.8.24"
        runtime ":resources:1.2.RC2"
        runtime ':bootstrap-theme:1.0.RC3'

        compile ":mail:1.0.1"
        compile ":svn:1.0.2"
        compile ":platform-core:1.0.RC5"
        compile ":platform-ui:1.0.RC3"
        compile ":ckeditor:3.6.3.0"
        compile ":rest-client-builder:1.0.2"

        test ":code-coverage:1.2.6"
        test ':codenarc:0.18.1'
        test ":spock:0.7"
    }
}

// Codenarc reports
codenarc.reports = {
    // Each report definition is of the form:
    //    REPORT-NAME(REPORT-TYPE) {
    //        PROPERTY-NAME = PROPERTY-VALUE
    //        PROPERTY-NAME = PROPERTY-VALUE
    //    }

    MyXmlReport('xml') {                    // The report name "MyXmlReport" is user-defined; Report type is 'xml'
        outputFile = 'target/test-reports/CodeNarcReport.xml'  // Set the 'outputFile' property of the (XML) Report
        title = 'Website Redesign CodeNarc Report'             // Set the 'title' property of the (XML) Report
    }

    MyHtmlReport('html') {                  // Report type is 'html'
        outputFile = 'target/test-reports/CodeNarcReport.html'
        title = 'Website Redesign CodeNarc Report'
    }
}

codenarc.properties = {
    CatchException.enabled = false
    ConstantAssertExpression.enabled = false
    EmptyIfStatement.priority = 1
    EmptyMethod.enabled = false
    EqualsAndHashCode.enabled = false
    GrailsDomainHasEquals.enabled = false
    GrailsDomainHasToString.enabled = false
    GrailsPublicControllerMethod.enabled = false
    MisorderedStaticImports.enabled = false
    ReturnNullFromCatchBlock.enabled = false
    UnusedVariable.ignoreVariableNames = 'username' //for the login controller
}
