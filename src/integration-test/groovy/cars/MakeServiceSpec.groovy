package cars

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MakeServiceSpec extends Specification {

    MakeService makeService
    SessionFactory sessionFactory

    private Long setupData() {
        new Make(name: "Tesla").save(flush: true, failOnError: true)
        Make make = new Make(name: "Ford").save(flush: true, failOnError: true)
        make.id
    }

    void "test get"() {
        Long makeId = setupData()

        expect:
        makeService.get(makeId) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Make> makeList = makeService.list(max: 2)

        then:
        makeList.size() == 2
    }

    void "test count"() {
        setupData()

        expect:
        makeService.count() > 0
    }

    void "test delete"() {
        Long makeId = setupData()

        int numOfMakes = makeService.count()

        when:
        makeService.delete(makeId)
        sessionFactory.currentSession.flush()

        then:
        makeService.count() == numOfMakes - 1
    }

    void "test save"() {
        when:
        Make make = new Make(name: "new make")
        makeService.save(make)

        then:
        make.id != null
    }
}
