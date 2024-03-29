package cars

import spock.lang.*
import static org.springframework.http.HttpStatus.*
import grails.validation.ValidationException
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest

class MakeControllerSpec extends Specification implements ControllerUnitTest<MakeController>, DomainUnitTest<Make> {

    def populateValidParams(params) {
        params["name"] = 'valid name'
    }

    void "Test the index action returns the correct response"() {
        given:
        controller.makeService = Mock(MakeService) {
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
        controller.makeService = Mock(MakeService) {
            1 * save(_ as Make)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def make = new Make(params)
        make.id = 1

        controller.save(make)

        then:
        response.status == CREATED.value()
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.makeService = Mock(MakeService) {
            1 * save(_ as Make) >> { Make make ->
                throw new ValidationException("Invalid instance", make.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        def make = new Make()
        make.validate()
        controller.save(make)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json.message != null
    }

    void "Test the show action with a null id"() {
        given:
        controller.makeService = Mock(MakeService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == NOT_FOUND.value()
    }

    void "Test the show action with a valid id"() {
        given:
        controller.makeService = Mock(MakeService) {
            1 * get(2) >> new Make(name: "valid name")
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        response.status == OK.value()
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
        controller.makeService = Mock(MakeService) {
            1 * save(_ as Make)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def make = new Make(params)
        make.id = 1

        controller.update(make)

        then:
        response.status == OK.value()
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.makeService = Mock(MakeService) {
            1 * save(_ as Make) >> { Make make ->
                throw new ValidationException("Invalid instance", make.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        def make = new Make()
        make.validate()
        controller.update(make)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json.message != null
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
        controller.makeService = Mock(MakeService) {
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