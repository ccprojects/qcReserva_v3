package org.grails.plugins.criteria

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.GrailsDomainClass

class CriteriaTagLib {

    private static loaded = false

    def criteriaService

    def criteria = {attrs, body ->
        if (!loaded) {
            if (criteriaService.hasPlugin("localizations")) {
                def test = message(code: "criteria.apply", default: "_missing!")
                if (test == "_missing!") {
                    def loc = ((GrailsDomainClass) ApplicationHolder.getApplication().getArtefact(DomainClassArtefactHandler.TYPE, "org.grails.plugins.localization.Localization")).newInstance()
                    loc.loadPluginData("criteria")
                }
            }

            loaded = true
        }

        def options = [:]
        if (attrs.domain) options.domain = attrs.domain[0].toUpperCase() + attrs.domain[1..-1]
        if (attrs.include) options.include = attrs.include.split(",")*.trim()
        if (attrs.exclude) options.exclude = attrs.exclude.split(",")*.trim()
        def props = criteriaService.getDomainProperties(params, options)
        if (props) {
            props.each {
                it.display = g.message(code: it.code, default: it.default)
            }

            props.sort {it.display.toString()}

            def url = g.link(controller: "criteria", action: "apply")
            url = url.substring(url.indexOf('"') + 1, url.lastIndexOf('"'))

            out << '<form action="' + url + '" method="post">\n'
            out << '  <input type="hidden" name="crController" value="' + params.controller + '"/>\n'
            out << '  <input type="hidden" name="crAction" value="' + params.action + '"/>\n'
            out << '  <input type="hidden" name="crDomain" value="' + criteriaService.getDomain(params, options) + '"/>\n'
            if (params.max) {
              out << '  <input type="hidden" name="max" value="' + params.max + '"/>\n'
            }
            if (params.offset) {
              out << '  <input type="hidden" name="offset" value="' + params.offset + '"/>\n'
            }
            if (params.sort) {
              out << '  <input type="hidden" name="sort" value="' + params.sort + '"/>\n'
            }
            if (params.order) {
              out << '  <input type="hidden" name="order" value="' + params.order + '"/>\n'
            }
            out << '  ' + g.message(code: "criteria.criteria", default: "Criteria") + ':\n'
            out << '  <select name="property">\n'

            def map = criteriaService.getCriteria(session, params)
            out << '    <option value="none"' + (!map ? " selected" : "") + '>' + g.message(code: "criteria.property.none", default: "-- none --") + '</option>\n'

            props.each {
              out << '    <option value="' + it.name + '"' + ((map?.property == it.name) ? " selected" : "") + '>' + it.display + '</option>\n'
            }

            out << '  </select>\n'
            out << '  <select name="test">\n'
            out << '    <option value="none"' + (!map ? " selected" : "") + '>' + g.message(code: "criteria.test.none", default: "-- none --") + '</option>\n'
            out << '    <option value="equal"' + ((map?.test == "equal") ? " selected" : "") + '>' + g.message(code: "criteria.test.equal", default: "Equal to") + '</option>\n'
            out << '    <option value="not.equal"' + ((map?.test == "not.equal") ? " selected" : "") + '>' + g.message(code: "criteria.test.not.equal", default: "Not equal to") + '</option>\n'
            out << '    <option value="null"' + ((map?.test == "null") ? " selected" : "") + '>' + g.message(code: "criteria.test.null", default: "Is null") + '</option>\n'
            out << '    <option value="not.null"' + ((map?.test == "not.null") ? " selected" : "") + '>' + g.message(code: "criteria.test.not.null", default: "Is not null") + '</option>\n'
            out << '    <option value="less"' + ((map?.test == "less") ? " selected" : "") + '>' + g.message(code: "criteria.test.less", default: "Less than") + '</option>\n'
            out << '    <option value="less.or.equal"' + ((map?.test == "less.or.equal") ? " selected" : "") + '>' + g.message(code: "criteria.test.less.or.equal", default: "Less than or equal to") + '</option>\n'
            out << '    <option value="greater"' + ((map?.test == "greater") ? " selected" : "") + '>' + g.message(code: "criteria.test.greater", default: "Greater than") + '</option>\n'
            out << '    <option value="greater.or.equal"' + ((map?.test == "greater.or.equal") ? " selected" : "") + '>' + g.message(code: "criteria.test.greater.or.equal", default: "Greater than or equal to") + '</option>\n'
            out << '    <option value="like"' + ((map?.test == "like") ? " selected" : "") + '>' + g.message(code: "criteria.test.like", default: "Like") + '</option>\n'
            out << '    <option value="not.like"' + ((map?.test == "not.like") ? " selected" : "") + '>' + g.message(code: "criteria.test.not.like", default: "Not like") + '</option>\n'
            out << '    <option value="between"' + ((map?.test == "between") ? " selected" : "") + '>' + g.message(code: "criteria.test.between", default: "Between") + '</option>\n'
            out << '    <option value="not.between"' + ((map?.test == "not.between") ? " selected" : "") + '>' + g.message(code: "criteria.test.not.between", default: "Not between") + '</option>\n'
            out << '    <option value="in"' + ((map?.test == "in") ? " selected" : "") + '>' + g.message(code: "criteria.test.in", default: "In") + '</option>\n'
            out << '    <option value="not.in"' + ((map?.test == "not.in") ? " selected" : "") + '>' + g.message(code: "criteria.test.not.in", default: "Not in") + '</option>\n'
            out << '  </select>\n'
            out << '  <input type="text" name="value" value="' + (map?.value ? map.value.encodeAsHTML() : "") + '"/>\n'
            out << '  <input class="apply" type="submit" value="' + g.message(code: "criteria.apply", default: "Apply") + '"/>\n'
            if (criteriaService.hasPlugin('helpBalloons')) {
                out << '  ' + g.helpBalloon(code: "criteria.criteria", encodeAs: "HTML")
            }
            out << '</form>\n'
        }
    }

    def criteriaReset = {attrs, body ->
        criteriaService.reset(session)
    }
}
