package cars

import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Make {
    String name

    static hasMany = [models: Model]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}