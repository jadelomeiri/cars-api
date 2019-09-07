package cars

import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Car {
    String make
    String model
    String colour
    Integer year
    String wordsSoundingLikeModel

    static constraints = {
        make nullable: false, blank: false
        model nullable: false, blank: false
        colour nullable: false, blank: false
        year nullable: false, blank: false
        wordsSoundingLikeModel nullable: true, blank: true
    }
}