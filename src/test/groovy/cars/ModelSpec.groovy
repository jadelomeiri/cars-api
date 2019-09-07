package cars

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ModelSpec extends Specification implements DomainUnitTest<Model> {

    void "Test the validation of a Model with all valid fields"() {
        given: "a new model with all valid fields"
        Make make = Mock(Make)
        Model model = new Model(name: "valid name", make: make)

        expect: "the validation passes"
        true == model.validate()
    }

    void "Test the validation of a Model with missing name"() {
        given: "a new model with a missing name"
        Make make = Mock(Make)
        Model model = new Model(make: make)

        expect: "the validation fails"
        false == model.validate()
    }

    void "Test the validation of a Model with missing make"() {
        given: "a new model with a missing make"
        Model model = new Model(name: "valid name")

        expect: "the validation fails"
        false == model.validate()
    }
}
