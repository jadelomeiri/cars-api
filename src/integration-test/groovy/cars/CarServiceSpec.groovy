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
        // TODO: Populate valid domain instances and return a valid ID
        //new Car(...).save(flush: true, failOnError: true)
        //new Car(...).save(flush: true, failOnError: true)
        //Car car = new Car(...).save(flush: true, failOnError: true)
        //new Car(...).save(flush: true, failOnError: true)
        //new Car(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //car.id
    }

    void "test get"() {
        setupData()

        expect:
        carService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Car> carList = carService.list(max: 2, offset: 2)

        then:
        carList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
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
        assert false, "TODO: Provide a valid instance to save"
        Car car = new Car()
        carService.save(car)

        then:
        car.id != null
    }
}
