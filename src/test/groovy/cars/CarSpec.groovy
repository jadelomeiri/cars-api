package cars

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CarSpec extends Specification implements DomainUnitTest<Car> {

    void "Test the validation of a Car with all valid fields"() {
        given: "a new car with all valid fields"
        Model model = Mock(Model)
        Car car = new Car(model: model, colour: "valid colour", year: 1996)

        expect: "the validation passes"
        true == car.validate()
    }

    void "Test the validation of a Car with a missing model"() {
        given: "a new car with a missing model"
        def car = new Car(colour: "valid colour", year: 1996)

        expect: "the validation fails"
        false == car.validate()
    }

    void "Test the validation of a Car with a missing colour"() {
        given: "a new car with a missing colour"
        Model model = Mock(Model)
        def car = new Car(model: model, year: 1996)

        expect: "the validation fails"
        false == car.validate()
    }

    void "Test the validation of a Car with a missing year"() {
        given: "a new car with a missing year"
        Model model = Mock(Model)
        def car = new Car(model: model, colour: "valid colour")

        expect: "the validation fails"
        false == car.validate()
    }
}
