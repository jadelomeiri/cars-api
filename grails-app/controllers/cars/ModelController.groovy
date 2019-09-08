package cars

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ModelController {

    ModelService modelService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        List<Model> modelList = modelService.list(params)
        int modelCount = modelService.count()

        render(view: "index", model: [modelList: modelList, modelCount: modelCount])
    }

    def show(Long id) {
        Model model = modelService.get(id)

        if(model == null) {
            render status: NOT_FOUND
            return
        }

        render(view: "show", model: [model: model])
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

        render(view: "show", model: [model: model], status: CREATED)
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

        render(view: "show", model: [model: model], status: OK)
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
