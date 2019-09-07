package cars

import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Model {
    String name

    static hasMany = [cars: Car]
    static belongsTo = [make: Make]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}