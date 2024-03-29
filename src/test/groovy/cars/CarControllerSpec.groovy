package cars

import spock.lang.*
import static org.springframework.http.HttpStatus.*
import grails.validation.ValidationException
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest

class CarControllerSpec extends Specification implements ControllerUnitTest<CarController>, DomainUnitTest<Car> {

    def populateValidParams(params) {
        Make make = new Make(name: "valid make").save(flush: true)
        Model model = new Model(name: "valid model", make: make).save(flush: true)

        params["model"] = model
        params["colour"] = 'valid colour'
        params["year"] = 1996
    }

    void "Test the index action returns the correct response"() {
        given:
        controller.carService = Mock(CarService) {
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
        controller.carService = Mock(CarService) {
            1 * save(_ as Car)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def car = new Car(params)
        car.id = 1

        controller.save(car)

        then:
        response.status == CREATED.value()
        //response.json
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.carService = Mock(CarService) {
            1 * save(_ as Car) >> { Car car ->
                throw new ValidationException("Invalid instance", car.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        def car = new Car()
        car.validate()
        controller.save(car)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json._embedded.errors != null
    }

    void "Test the show action with a null id"() {
        given:
        controller.carService = Mock(CarService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == NOT_FOUND.value()
    }

    void "Test the show action with a valid id"() {
        given:
        controller.carService = Mock(CarService) {
            populateValidParams(params)
            1 * get(2) >> new Car(params)
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
        controller.carService = Mock(CarService) {
            1 * save(_ as Car)
        }

        when:
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def car = new Car(params)
        car.id = 1

        controller.update(car)

        then:
        response.status == OK.value()
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.carService = Mock(CarService) {
            1 * save(_ as Car) >> { Car car ->
                throw new ValidationException("Invalid instance", car.errors)
            }
        }

        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'
        def car = new Car()
        car.validate()
        controller.update(car)

        then:
        response.status == UNPROCESSABLE_ENTITY.value()
        response.json._embedded.errors != null
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
        controller.carService = Mock(CarService) {
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