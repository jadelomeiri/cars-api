package cars

import grails.validation.ValidationException
import javax.servlet.http.HttpServletRequest
import static org.springframework.http.HttpStatus.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON

class CarController {

    private static final String DATAMUSE_API_ENDPOINT = "https://api.datamuse.com/words?max=3&sl="

    CarService carService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        List<Car> carList = carService.list(params)
        int carCount = carService.count()

        render(view: "index", model: [carList: carList, carCount: carCount])
    }

    def show(Long id) {
        Car car = carService.get(id)

        if(car == null) {
            render status: NOT_FOUND
            return
        }

        render(view: "show", model: [car: car])
    }

    def save(Car car) {
        if (car == null) {
            render status: NOT_FOUND
            return
        }

        //getting words from DATAMUSE API
        String wordsSoundingLikeModel = getWordsSoundingLike(car?.model?.name)
        car.wordsSoundingLikeModel = wordsSoundingLikeModel

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

    private String getWordsSoundingLike(String word) {
        if(!word) return ""

        String wordEncoded = URLEncoder.encode(word, "UTF-8")

        def http = new HTTPBuilder(DATAMUSE_API_ENDPOINT + wordEncoded)
        http.request(Method.GET, JSON) {
            response.success = { resp, json ->
                return json.word
            }
        }
    }
}
