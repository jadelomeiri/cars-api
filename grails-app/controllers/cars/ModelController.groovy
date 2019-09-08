package cars

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ModelController {

    ModelService modelService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond modelService.list(params), model:[modelCount: modelService.count()]
    }

    def show(Long id) {
        respond modelService.get(id)
    }

    def save(Model model) {
        if (model == null) {
            render status: NOT_FOUND
            return
        }

        try {
            modelService.save(model)
        } catch (ValidationException e) {
            respond model.errors, view:'create'
            return
        }

        respond model, [status: CREATED, view:"show"]
    }

    def update(Model model) {
        if (model == null) {
            render status: NOT_FOUND
            return
        }

        try {
            modelService.save(model)
        } catch (ValidationException e) {
            respond model.errors, view:'edit'
            return
        }

        respond model, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        modelService.delete(id)

        render status: NO_CONTENT
    }
}
