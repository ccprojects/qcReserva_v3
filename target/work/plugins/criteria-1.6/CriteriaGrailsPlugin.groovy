class CriteriaGrailsPlugin {
    def version = 1.6
    def dependsOn = [:]

    // TODO Fill in these fields
    def author = "Paul Fernley"
    def authorEmail = "paul@pfernley.orangehome.co.uk"
    def title = "Criteria (record filtering) plugin"
    def description = '''\
This plugin allows the user to limit the display of records, typically on a
'list' page, by specifying a property to be tested, the test to perform and,
where applicable, the value(s) to test against. Only domain properties with a
Hibernate 'basic data type' (including enumerations) can be tested. The tests
available are: equal, not equal, is null, is not null, less than, less than or
equal to, greater than, greater than or equal to, like, not like, between,
not between, in and not in.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/Criteria+Plugin"

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }

    def doWithDynamicMethods = { ctx ->
        application.domainClasses.each { domainClass ->

            domainClass.metaClass.static.selectList = {
                org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession session,
                org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap params ->

                def queryStatement = ""
                def joins = ""
                def joinId
                def first = true
                def queryParameters = []
                def selectors = session.selectors
                def selector = null
                if (selectors) {
                    def controller = params.controller
                    def action = params.action
                    if (controller && action) {
                        selector = selectors.get(controller + "." + action)
                        if (selector?.members) {
                            selector.members.each { key, member ->
                                def prefix = 'x'
                                def join = member.get('joinProperty')
                                if (join) {
                                    joinId = member.get('joinId')
                                    prefix = join + '_ref'
                                    joins += " join x.${join} as ${prefix}"
                                }

                                def properties = member.get('properties')
                                def tests = member.get('tests')
                                def parameters = member.get('parameters')

                                for (int i = 0; i < properties.size(); i++) {
                                    if (first) {
                                        queryStatement = " where ${prefix}."
                                        first = false
                                    } else {
                                        queryStatement += " and ${prefix}."
                                    }

                                    queryStatement += properties[i]
                                    switch (tests[i]) {
                                        case "equal":
                                        queryStatement += ' = ?'
                                        break

                                        case "not.equal":
                                        queryStatement += ' != ?'
                                        break

                                        case "less":
                                        queryStatement += ' < ?'
                                        break

                                        case "less.or.equal":
                                        queryStatement += ' <= ?'
                                        break

                                        case "greater":
                                        queryStatement += ' > ?'
                                        break

                                        case "greater.or.equal":
                                        queryStatement += ' >= ?'
                                        break

                                        case "null":
                                        queryStatement += ' is null'
                                        break

                                        case "not.null":
                                        queryStatement += ' is not null'
                                        break

                                        case "like":
                                        queryStatement += ' like ?'
                                        break

                                        case "not.like":
                                        queryStatement += ' not like ?'
                                        break

                                        case "between":
                                        queryStatement += ' between ? and ?'
                                        break

                                        case "not.between":
                                        queryStatement += ' not between ? and ?'
                                        break

                                        case "in":
                                        queryStatement += ' in ('
                                        for (int j = 0; j < parameters[i].size(); j++) {
                                            queryStatement += (j == 0) ? "?" : ", ?"
                                        }
                                        queryStatement += ")"
                                        break

                                        case "not.in":
                                        queryStatement += ' not in ('
                                        for (int j = 0; j < parameters[i].size(); j++) {
                                            queryStatement += (j == 0) ? "?" : ", ?"
                                        }
                                        queryStatement += ")"
                                        break

                                        default:
                                        def msg = "Unknown selector test of '${tests[i]}'"
                                        log.error(msg)
                                        throw new IllegalArgumentException(msg)
                                    }

                                    if (parameters) {
                                        queryParameters.addAll(parameters[i])
                                    }
                                }
                            }
                        } else {
                            selector = null
                        }
                    }
                }

                queryStatement = "from ${delegate.name} as x" + joins + queryStatement

                if (selector) {
                    selector.queryStatement = queryStatement
                    selector.queryParameters = queryParameters
                }

                if (joins) queryStatement = "from ${delegate.name} as y where y.id in (select x.id ${queryStatement})"

                def max = params.max ? params.max.toInteger() : 1000
                def offset = params.offset ? params.offset.toInteger() : 0
                def sort = params.sort
                def order = params.order ?: "asc"
                if (sort) queryStatement += " order by ${joins ? 'y' : 'x'}.${sort} ${order}"

                log.debug("${queryStatement}; Parameters = ${queryParameters}; Pagination = [max:${max}, offset:${offset}]")
                return delegate.findAll(queryStatement, queryParameters, [max: max, offset: offset])
            }

            domainClass.metaClass.static.selectCount = {
                org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession session,
                org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap params ->

                def queryStatement = "select count(*) from ${delegate.name} as x"
                def queryParameters = []
                def selectors = session.selectors
                if (selectors) {
                    def controller = params.controller
                    def action = params.action
                    if (controller && action) {
                        def selector = selectors.get(controller + "." + action)
                        if (selector?.queryStatement) {
                            queryStatement = "select count(*) " + selector.queryStatement
                            queryParameters = selector.queryParameters
                        }
                    }
                }

                log.debug("${queryStatement}; Parameters = ${queryParameters}")
                return delegate.executeQuery(queryStatement, queryParameters)[0]
            }
        }
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
