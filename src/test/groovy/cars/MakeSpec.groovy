package cars

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MakeSpec extends Specification implements DomainUnitTest<Make> {

    void "Test the validation of a Make with a valid name"() {
        given: "a new make with a valid name"
        Make make = new Make(name: "valid name")

        expect: "the validation passes"
        true == make.validate()
    }

    void "Test the validation of a Make with missing name"() {
        given: "a new make with a missing name"
        Make make = new Make()

        expect: "the validation fails"
        false == make.validate()
    }
}
