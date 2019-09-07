package cars

import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Car {
    String colour
    Integer year
    String wordsSoundingLikeModel

    static belongsTo = [model: Model]

    static constraints = {
        colour nullable: false, blank: false
        year nullable: false, blank: false
        wordsSoundingLikeModel nullable: true, blank: true
    }
}