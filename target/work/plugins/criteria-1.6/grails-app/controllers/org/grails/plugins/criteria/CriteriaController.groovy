package org.grails.plugins.criteria
class CriteriaController {

    def criteriaService

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [apply:'POST']

    def apply = {

        // Grab the parameters
        def property = params.property
        def test = params.test
        def value = params.value
        def crController = params.crController
        def crAction = params.crAction
        def crDomain = params.crDomain

        // Clean up the parameters
        params.remove("property")
        params.remove("test")
        params.remove("value")
        params.remove("crController")
        params.remove("crAction")
        params.remove("crDomain")

        // Clean up the user responses if required
        if (property == "none" || test == "none" ) {
            property = "none"
            test = "none"
            value = ""
        } else if (test == "null" || test == "not.null") {
            value = ""
        }

        // Get our service to store the info required for the target controller/action
        if (criteriaService.apply(session, crController, crAction, crDomain, property, test, value)) {
            flash.message = message(code: 'criteria.applied', default: 'Criteria changes applied')

            // Need to start on the first page if new criteria
            if (params.offset) params.remove("offset")
        } else {
            flash.message = message(code: 'criteria.invalid', default: 'Invalid criteria, no changes made')
        }

        // Redirect to the target controller/action
        redirect(controller: crController, action: crAction, params: params)
    }
}
