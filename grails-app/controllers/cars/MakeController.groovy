package cars

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MakeController {

    MakeService makeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond makeService.list(params), model:[makeCount: makeService.count()]
    }

    def show(Long id) {
        respond makeService.get(id)
    }

    def save(Make make) {
        if (make == null) {
            render status: NOT_FOUND
            return
        }

        try {
            makeService.save(make)
        } catch (ValidationException e) {
            respond make.errors, view:'create'
            return
        }

        respond make, [status: CREATED, view:"show"]
    }

    def update(Make make) {
        if (make == null) {
            render status: NOT_FOUND
            return
        }

        try {
            makeService.save(make)
        } catch (ValidationException e) {
            respond make.errors, view:'edit'
            return
        }

        respond make, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        makeService.delete(id)

        render status: NO_CONTENT
    }
}
