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
        // 3 cars are being populated in Bootstrap.groovy
        Car car1 = new Car(make: "make 1", model: "model 1", colour: "colour 1", year: 2001).save(flush: true, failOnError: true)
        Car car2 = new Car(make: "make 2", model: "model 2", colour: "colour 2", year: 2002).save(flush: true, failOnError: true)
        car2.id
    }

    void "test get"() {
        setupData()

        expect:
        carService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Car> carList = carService.list()

        then:
        carList.size() == 5
        //assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        carService.count() == 5
    }

    void "test delete"() {
        Long carId = setupData()

        expect:
        carService.count() == 5

        when:
        carService.delete(carId)
        sessionFactory.currentSession.flush()

        then:
        carService.count() == 4
    }

    void "test save"() {
        when:
        Car car = new Car(make: "new make", model: "new model", colour: "new colour", year: 1990)
        carService.save(car)

        then:
        car.id != null
    }
}
