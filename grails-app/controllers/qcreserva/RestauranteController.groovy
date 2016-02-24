package qcreserva



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RestauranteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Restaurante.list(params), model:[restauranteInstanceCount: Restaurante.count()]
    }

    def show(Restaurante restauranteInstance) {
        respond restauranteInstance
    }

    def create() {
        respond new Restaurante(params)
    }

    @Transactional
    def save(Restaurante restauranteInstance) {
        if (restauranteInstance == null) {
            notFound()
            return
        }

        if (restauranteInstance.hasErrors()) {
            respond restauranteInstance.errors, view:'create'
            return
        }

        restauranteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'restaurante.label', default: 'Restaurante'), restauranteInstance.id])
                redirect restauranteInstance
            }
            '*' { respond restauranteInstance, [status: CREATED] }
        }
    }

    def edit(Restaurante restauranteInstance) {
        respond restauranteInstance
    }

    @Transactional
    def update(Restaurante restauranteInstance) {
        if (restauranteInstance == null) {
            notFound()
            return
        }

        if (restauranteInstance.hasErrors()) {
            respond restauranteInstance.errors, view:'edit'
            return
        }

        restauranteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Restaurante.label', default: 'Restaurante'), restauranteInstance.id])
                redirect restauranteInstance
            }
            '*'{ respond restauranteInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Restaurante restauranteInstance) {

        if (restauranteInstance == null) {
            notFound()
            return
        }

        restauranteInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Restaurante.label', default: 'Restaurante'), restauranteInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'restaurante.label', default: 'Restaurante'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
