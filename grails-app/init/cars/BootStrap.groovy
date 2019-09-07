package cars

import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        List<Car> allCars = Car.findAll()
        boolean noCarsInDB = allCars == null || allCars.isEmpty()

        if(noCarsInDB) {
            Make make1 = new Make(name: "Audi").save(flush: true)
            Make make2 = new Make(name: "Kia").save(flush: true)

            Model model1 = new Model(name: "A4", make: make1).save(flush: true)
            Model model2 = new Model(name: "A3", make: make1).save(flush: true)
            Model model3 = new Model(name: "Cerato", make: make2).save(flush: true)

            Car car1 = new Car(model: model1, colour: "colour 1", year: 2001).save(flush: true)
            Car car2 = new Car(model: model2, colour: "colour 2", year: 2002).save(flush: true)
            Car car3 = new Car(model: model3, colour: "colour 2", year: 2003).save(flush: true)
        }

    }
    def destroy = {
    }
}
