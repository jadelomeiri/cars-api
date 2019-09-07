package cars


import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Car {
    String make
    String model
    String colour
    int year

    static constraints = {
        make nullable: false, blank: false
        model nullable: false, blank: false
        colour nullable: false, blank: false
        year nullable: false, blank: false
    }
}