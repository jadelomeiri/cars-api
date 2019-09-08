package cars

import spock.lang.*
import static org.springframework.http.HttpStatus.*
import grails.validation.ValidationException
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest

class ModelControllerSpec extends Specification implements ControllerUnitTest<ModelController>, DomainUnitTest<Model> {

    def populateValidParams(params) {
        Make make = new Make(name: "valid make").save(flush: true)

        params["name"] = 'valid name'
        params["make"] = make
    }

    void "Test the index action returns the correct response"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.text == ''
    }


    void "Test the save action with a null instance"() {
        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        controller.save(null)

        then:
        response.status == NOT_FOUND.value()
    }

    void "Test the save action correctly persists"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * save(_ as Model)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def model = new Model(params)
        model.id = 1

        controller.save(model)

        then:
        response.status == CREATED.value()
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * save(_ as Model) >> { Model model ->
                throw new ValidationException("Invalid instance", model.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        def model = new Model()
        model.validate()
        controller.save(model)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json.message != null || response.json._embedded.errors != null
    }

    void "Test the show action with a null id"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == NOT_FOUND.value()
    }

    void "Test the show action with a valid id"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * get(2) >> new Model()
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        response.status == OK.value()
        //response.json
    }

    void "Test the update action with a null instance"() {
        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:
        response.status == NOT_FOUND.value()
    }

    void "Test the update action correctly persists"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * save(_ as Model)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def model = new Model(params)
        model.id = 1

        controller.update(model)

        then:
        response.status == OK.value()
        //response.json
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * save(_ as Model) >> { Model model ->
                throw new ValidationException("Invalid instance", model.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        def model = new Model()
        model.validate()
        controller.update(model)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json.message != null || response.json._embedded.errors != null
    }

    void "Test the delete action with a null instance"() {
        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:
        response.status == NOT_FOUND.value()
    }

    void "Test the delete action with an instance"() {
        given:
        controller.modelService = Mock(ModelService) {
            1 * delete(2)
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:
        response.status == NO_CONTENT.value()
    }
}