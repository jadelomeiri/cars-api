package cars

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CarServiceSpec extends Specification {

    CarService carService
    SessionFactory sessionFactory

    private Long setupData() {
        Make make = new Make(name: "Alpha Romeo").save(flush: true)
        Model model = new Model(name: "Giulietta", make: make).save(flush: true)

        // 3 cars are being populated in Bootstrap.groovy
        Car car1 = new Car(model: model, colour: "colour 1", year: 2001).save(flush: true, failOnError: true)
        Car car2 = new Car(model: model, colour: "colour 2", year: 2002).save(flush: true, failOnError: true)
        car2.id
    }

    void "test get"() {
        Long carId = setupData()

        expect:
        carService.get(carId) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Car> carList = carService.list(max: 2)

        then:
        carList.size() == 2
    }

    void "test count"() {
        setupData()

        expect:
        carService.count() > 0
    }

    void "test delete"() {
        Long carId = setupData()

        int numOfCars = carService.count()

        when:
        carService.delete(carId)
        sessionFactory.currentSession.flush()

        then:
        carService.count() == numOfCars - 1
    }

    void "test save"() {
        when:
        Make make = new Make(name: "Mitsubishi").save(flush: true)
        Model model = new Model(name: "Lancer EX", make: make).save(flush: true)
        Car car = new Car(model: model, colour: "white", year: 2019)
        carService.save(car)

        then:
        car.id != null
    }
}
