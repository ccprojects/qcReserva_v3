package org.grails.plugins.criteria

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.exceptions.InvalidPropertyException
import java.text.SimpleDateFormat
import java.text.ParseException

class CriteriaService {

    boolean transactional = true

    private static final domainClasses = [:]


    static HashSet criteriaTypes = new HashSet([
            "char",
            "java.lang.Character",
            "java.lang.String",
            "java.util.Currency",
            "java.util.Locale",
            "java.util.TimeZone",
            "byte",
            "java.lang.Byte",
            "short",
            "java.lang.Short",
            "int",
            "java.lang.Integer",
            "long",
            "java.lang.Long",
            "java.math.BigInteger",
            "float",
            "java.lang.Float",
            "double",
            "java.lang.Double",
            "java.math.BigDecimal",
            "java.util.Date",
            "java.sql.Date",
            "java.sql.Time",
            "java.sql.Timestamp",
            "java.util.Calendar",
            "boolean",
            "java.lang.Boolean"
        ])

    def hasPlugin(name) {
        return org.codehaus.groovy.grails.plugins.PluginManagerHolder.getPluginManager()?.hasGrailsPlugin(name)
    }

    def getDomainProperties(params, options = null) {
        def dom = getDomain(params, options)
        def codePrefix = dom[0].toLowerCase() + dom[1..-1] + "."
        def include = options?.include
        def exclude = options?.exclude
        GrailsDomainClass dc = getGrailsDomainClass(dom)
        def props = []
        appendProperty(dc.getIdentifier(), props, include, exclude, codePrefix, "")

        try {
          appendProperty(dc.getVersion(), props, include, exclude, codePrefix, "")
        } catch (InvalidPropertyException ipe1) {}

        try {
          appendProperty(dc.getPropertyByName("dateCreated"), props, include, exclude, codePrefix, "")
        } catch (InvalidPropertyException ipe2) {}

        try {
          appendProperty(dc.getPropertyByName("lastUpdated"), props, include, exclude, codePrefix, "")
        } catch (InvalidPropertyException ipe3) {}

        processPersistents(dc, props, include, exclude)

        return props
    }

    def getDomain(params, options) {
        return options?.domain ?: params.controller[0].toUpperCase() + params.controller[1..-1]
    }

    def apply(session, controller, action, domain, property, test, value) {
        GrailsDomainClass dc = getGrailsDomainClass(domain)

        def valid = true

        if (property == "none") {
            removeCriteria(session, controller + "." + action)
        } else {
            def prop

            property.tokenize(".").each {
                try {   // Bug in Grails 1.0.3
                    prop = dc.getPropertyByName(it)
                } catch (Exception ex) {
                    prop = dc.getPropertyByName(it[0].toUpperCase() + it[1..-1])
                }

                // Switch the domain class to the embedded class
                if (prop.isEmbedded()) dc = prop.getComponent()
            }

            def type = getPropertyType(prop)
            def values = []
            def val
            switch (test) {
                case "equal":
                case "not.equal":
                case "less":
                case "less.or.equal":
                case "greater":
                case "greater.or.equal":
                    val = getValue(type, value, test)
                    if (val != null) {
                        values << val
                    } else {
                        valid = false
                        break
                    }
                    break

                case "null":
                case "not.null":
                    // Nothing to do
                    break

                case "like":
                case "not.like":
                    val = getValue(type, value, test)
                    if (val != null) {
                        values << val
                    } else {
                        valid = false
                        break
                    }
                    break

                case "between":
                case "not.between":
                    def vals = value.split(" & ")*.trim()
                    if (vals.size() == 2) {
                          for (String v : vals) {
                              val = getValue(type, v, test)
                              if (val != null) {
                                  values << val
                              } else {
                                  valid = false
                                  break
                              }
                          }
                    } else {
                        valid = false
                    }
                    break

                case "in":
                case "not.in":
                    def vals = value.split(" | ")*.trim()
                    for (String v : vals) {

                        // Weirdo in groovy split functionality with pipe character
                        if (v != "|") {
                            val = getValue(type, v, test)
                            if (val != null) {
                                values << val
                            } else {
                                valid = false
                                break
                            }
                        }
                    }
                    break

                default:
                    valid = false
                    break
            }

            if (valid) {
                setCriteria(session, controller + "." + action, property, test, values, value)
            }
        }

        return valid
    }

    def getCriteria(session, params) {
        return getSelectors(session).get(params.controller + "." + params.action)?.get("members")?.get("criteria") ?: [:]
    }

    def reset(session) {
        def selectors = getSelectors(session)
        def removables = []
        def members
        selectors.each {key, selector ->
            members = selector.get("members")
            if (members?.containsKey("criteria")) {
                members.remove("criteria")
                if (members.size() == 0) {
                    selector.remove("members")
                }

                selector.remove("queryStatement")
                selector.remove("queryParameters")

                if (selector.size() == 0) removables << key
            }
        }

        removables.each { key ->
            selectors.remove(key)
        }
    }

    def removeCriteria(session, key) {
        def selectors = getSelectors(session)
        if (selectors) {
            def selector = selectors.get(key)
            if (selector) {
                def members = selector.members
                if (members.containsKey("criteria")) {
                    members.remove("criteria")
                    if (members.size() == 0) {
                        selector.remove("members")
                    }

                    selector.remove("queryStatement")
                    selector.remove("queryParameters")
                    if (selector.size() == 0) {
                        selectors.remove(key)
                    }
                }
            }
        }
    }

    private processPersistents(domainClass, props, include, exclude, defaultPrefix = "", propertyPrefix = "") {
        def name
        def domain = domainClass.getPropertyName()
        def codePrefix = domain[0].toLowerCase() + domain[1..-1] + "."

        domainClass.getPersistentProperties().each {

            // Deal with embedded classes
            if (it.isEmbedded()) {
                processPersistents(it.getComponent(), props, include, exclude, it.getNaturalName() + " - ", getPropertyName(it) + ".")
            } else {
                name = it.getName()

                // Do not included id, version, dateCreated or lastUpdated for
                // embedded classes. The parent domain class has had them
                // 'manually' included already.
                if (!it.isIdentity() && name != "version" && name != "dateCreated" && name != "lastUpdated") {
                  appendProperty(it, props, include, exclude, codePrefix, defaultPrefix, propertyPrefix)
                }
            }
        }
    }

    private getValue(type, val, test) {

        // Need some sort of value. We return null to mean 'invalid value'
        if (val != null) {

            // 'Like' and 'Not Like' tests need at least two characters to make any sense
            if (test == "like" || test == "not.like") {

                // A valid Like/Not like value cannot be type checked (e.g. for
                // a valid Currency code etc), so just pass it back, but such
                // tests can only be applied to string type fields
                if (val.length() < 2
                    || (type != "java.lang.String"
                        && type != "java.util.Currency"
                        && type != "java.util.Locale"
                        && type != "java.util.TimeZone")) {

                    val = null
                }
            } else if (val.length() == 0 && type != "java.lang.String") {

                // Only String data types may be tested againt a blank value
                val = null
            } else if ((type == "boolean" || type == "java.lang.Boolean")
                  && test != "equal" && test != "not.equal"
                  && test != "null" && test != "not.null") {

                // Boolean values can only be tested for equal, not equal, null and not null
                val = null
            } else if (!(type instanceof String)) {

                // Check for enum where the 'type' is actually an array of
                // the enum constants
                def found = false
                for (int i = 0; i < type.length; i++) {
                    if (type[i].toString() == val) {
                        val = type[i]
                        found = true
                        break
                    }
                }

                if (!found) val = null
            } else {
                switch (type) {
                    case "java.lang.String":
                        // Nothing to do since it's already a String
                        break;

                    case "char":
                    case "java.lang.Character":
                        if (val.length() == 1) {
                            val = new Character(val.charAt(0))
                        } else {
                            val = null
                        }
                        break;

                    case "java.util.Currency":
                        try {
                            val = Currency.getInstance(val)
                        } catch (IllegalArgumentException curex) {
                            val = null
                        }
                        break

                    case "java.util.Locale":
                        if (val ==~ /[a-z][a-z]/) {
                            val = new Locale(val)
                        } else if (val ==~ /[a-z][a-z]_[A-Z][A-Z]/) {
                            val = new Locale(val[0..1], val[3..4])
                        } else {
                            val = null
                        }

                        break

                    case "java.util.TimeZone":
                        val = TimeZone.getTimeZone(val)
                        break

                    case "byte":
                    case "java.lang.Byte":
                        try {
                            val = Byte.valueOf(val)
                        } catch (NumberFormatException byex) {
                            val = null
                        }
                        break

                    case "short":
                    case "java.lang.Short":
                        try {
                            val = Short.valueOf(val)
                        } catch (NumberFormatException shex) {
                            val = null
                        }
                        break

                    case "int":
                    case "java.lang.Integer":
                        try {
                            val = Integer.valueOf(val)
                        } catch (NumberFormatException inex) {
                            val = null
                        }
                        break

                    case "long":
                    case "java.lang.Long":
                        try {
                            val = Long.valueOf(val)
                        } catch (NumberFormatException loex) {
                            val = null
                        }
                        break

                    case "java.math.BigInteger":
                        try {
                            val = new BigInteger(val)
                        } catch (NumberFormatException biex) {
                            val = null
                        }
                        break

                    case "float":
                    case "java.lang.Float":
                        try {
                            val = Float.valueOf(val)
                        } catch (NumberFormatException flex) {
                            val = null
                        }
                        break

                    case "double":
                    case "java.lang.Double":
                        try {
                            val = Double.valueOf(val)
                        } catch (NumberFormatException duex) {
                            val = null
                        }
                        break

                    case "java.math.BigDecimal":
                        try {
                            val = new BigDecimal(val)
                        } catch (NumberFormatException bdex) {
                            val = null
                        }
                        break

                    case "java.util.Date":
                        val = parseDate(val)
                        break

                    case "java.util.Calendar":
                        def dt = parseDate(val)
                        if (dt) {
                            val = Calendar.getInstance()
                            val.setTime(dt)
                        } else {
                            val = null
                        }
                        break

                    case "java.sql.Date":
                        if (val.length() == 10) {
                            try {
                                val = java.sql.Date.valueOf(val)
                            } catch (IllegalArgumentException sd) {
                                val = null
                            }
                        } else {
                            val = null
                        }
                        break

                    case "java.sql.Time":
                        if (val.length() == 5) {
                            try {
                                val = java.sql.Time.valueOf(val + ":00")
                            } catch (IllegalArgumentException sd) {
                                val = null
                            }
                        } else {
                            val = null
                        }
                        break

                    case "java.sql.Timestamp":
                        if (val.length() == 16) {
                            try {
                                val = java.sql.Timestamp.valueOf(val + ":00")
                            } catch (IllegalArgumentException sd) {
                                val = null
                            }
                        } else {
                            val = null
                        }
                        break

                    case "boolean":
                    case "java.lang.Boolean":
                        if (val.equalsIgnoreCase("true")) {
                            val = Boolean.TRUE
                        } else if (val.equalsIgnoreCase("false")) {
                            val = Boolean.FALSE
                        } else {
                            val = null
                        }
                        break

                    default:  // Unknown data type
                        val = null
                        break
                }
            }
        }

        return val
    }

    private parseDate(val) {
        if (val.length() == 10) {
            try {
                val = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(val)
            } catch (ParseException ud1) {
                val = null
            }
        } else {
            try {
                val = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(val)
            } catch (ParseException ud2) {
                val = null
            }
        }

        return val
    }

    private appendProperty(prop, props, include, exclude, codePrefix, defaultPrefix, propertyPrefix = "") {
        if (prop) {
            def type = getPropertyType(prop)
            if (type) {
                def name = propertyPrefix + getPropertyName(prop)
                if (include) {
                    if (!include.contains(name)) {
                        prop = null
                    }
                } else if (exclude) {
                    if (exclude.contains(getPropertyName(prop))) {
                        prop = null
                    }
                }

                if (prop) {
                    props << [name: name, type: type, code: codePrefix + prop.getName(), default: defaultPrefix + prop.getNaturalName()]
                }
            }
        }
    }

    private getPropertyType(prop) {

        // Check for enums
        if (prop.isEnum()) {

            // Return a list of the enum constants as it's 'type'
            return prop.getType().getEnumConstants()
        }

        return criteriaTypes.contains(prop.getType().name) ? prop.getType().name : null
    }

    private getPropertyName(prop) {
        def name = prop.getName()
        return name[0].toLowerCase() + name[1..-1]  // Bug in Grails 1.0.3
    }

    private setCriteria(session, key, property, test, values, value) {
        def selectors = getSelectors(session)
        def selector = selectors.get(key)
        if (selector == null) {
            selector = [:]
            selectors.put(key, selector)
        } else {
            selector.remove("queryStatement")
            selector.remove("queryParameters")
        }

        def members = selector.members
        if (members == null) {
            members = [:]
            selector.put("members", members)
        }

        def properties = [property]
        def tests = [test]
        def parameters = [values]
        def member = [:]
        member.put("properties", properties)
        member.put("tests", tests)
        member.put("parameters", parameters)
        member.put("property", property)
        member.put("test", test)
        member.put("value", value)

        members.put("criteria", member)
    }

    private getSelectors(session) {

        // Get the map of selectors for the session, creating it if required
        def selectors = session.selectors
        if (selectors == null) {
            selectors = [:]
            session.selectors = selectors
        }

        return selectors
    }

    // Return a Grails domain class
    static GrailsDomainClass getGrailsDomainClass(domain) {
        synchronized (domainClasses) {
            if (domainClasses.size() == 0) {
                ApplicationHolder.application.getArtefacts(DomainClassArtefactHandler.TYPE).each {
                    domainClasses.put(it.name, it)
                }
            }
        }

        return (GrailsDomainClass) domainClasses.get(domain)
    }
}
