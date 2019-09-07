package cars

import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        if (Environment.current == Environment.DEVELOPMENT) {
            Car car1 = new Car(make: "make 1", model: "model 1", colour: "colour 1", year: 2001).save(flush: true)
            Car car2 = new Car(make: "make 2", model: "model 1", colour: "colour 1", year: 2001).save(flush: true)
            Car car3 = new Car(make: "make 3", model: "model 1", colour: "colour 1", year: 2001).save(flush: true)
        }
    }
    def destroy = {
    }
}
