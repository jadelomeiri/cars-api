package cars

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CarSpec extends Specification implements DomainUnitTest<Car> {

    void "Test the validation of a Car with all valid fields"() {
        given: "a new car with all valid fields"
        def car = new Car(make: "valid make", model: "valid model", colour: "valid colour", year: 1996)

        expect: "the validation passes"
        true == car.validate()
    }

    void "Test the validation of a Car with a missing make"() {
        given: "a new car with a missing make"
        def car = new Car(model: "valid model", colour: "valid colour", year: 1996)

        expect: "the validation fails"
        false == car.validate()
    }

    void "Test the validation of a Car with a missing model"() {
        given: "a new car with a missing make"
        def car = new Car(make: "valid make", colour: "valid colour", year: 1996)

        expect: "the validation fails"
        false == car.validate()
    }

    void "Test the validation of a Car with a missing colour"() {
        given: "a new car with a missing make"
        def car = new Car(make: "valid make", model: "valid model", year: 1996)

        expect: "the validation fails"
        false == car.validate()
    }

    void "Test the validation of a Car with a missing year"() {
        given: "a new car with a missing make"
        def car = new Car(make: "valid make", model: "valid model", colour: "valid colour")

        expect: "the validation fails"
        false == car.validate()
    }
}
