//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
// Ant.mkdir(dir:"C:\Users\Paul\Documents\NetBeansProjects/criteria/grails-app/jobs")
//

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
Ant.copy(file:"${pluginBasedir}/grails-app/i18n/criteria.properties",
         todir:"${basedir}/grails-app/i18n")
