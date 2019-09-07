package cars

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CarController {

    CarService carService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond carService.list(params), model:[carCount: carService.count()]
    }

    def show(Long id) {
        respond carService.get(id)
    }

    def save(Car car) {
        if (car == null) {
            render status: NOT_FOUND
            return
        }

        try {
            carService.save(car)
        } catch (ValidationException e) {
            respond car.errors, view:'create'
            return
        }

        respond car, [status: CREATED, view:"show"]
    }

    def update(Car car) {
        if (car == null) {
            render status: NOT_FOUND
            return
        }

        try {
            carService.save(car)
        } catch (ValidationException e) {
            respond car.errors, view:'edit'
            return
        }

        respond car, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        carService.delete(id)

        render status: NO_CONTENT
    }
}
