package cars

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MakeController {

    MakeService makeService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        List<Make> makeList = makeService.list(params)
        int makeCount = makeService.count()

        render(view: "index", model: [makeList: makeList, makeCount: makeCount])
    }

    def show(Long id) {
        Make make = makeService.get(id)

        if(make == null) {
            render status: NOT_FOUND
            return
        }

        render(view: "show", model: [make: make])
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

        render(view: "show", model: [make: make], status: CREATED)
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

        render(view: "show", model: [make: make], status: OK)
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
