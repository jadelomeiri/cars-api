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
        // TODO: Populate valid domain instances and return a valid ID
        //new Make(...).save(flush: true, failOnError: true)
        //new Make(...).save(flush: true, failOnError: true)
        //Make make = new Make(...).save(flush: true, failOnError: true)
        //new Make(...).save(flush: true, failOnError: true)
        //new Make(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //make.id
    }

    void "test get"() {
        setupData()

        expect:
        makeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Make> makeList = makeService.list(max: 2, offset: 2)

        then:
        makeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        makeService.count() == 5
    }

    void "test delete"() {
        Long makeId = setupData()

        expect:
        makeService.count() == 5

        when:
        makeService.delete(makeId)
        sessionFactory.currentSession.flush()

        then:
        makeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Make make = new Make()
        makeService.save(make)

        then:
        make.id != null
    }
}
