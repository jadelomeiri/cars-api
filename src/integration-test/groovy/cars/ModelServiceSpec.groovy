package cars

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ModelServiceSpec extends Specification {

    ModelService modelService
    SessionFactory sessionFactory

    private Long setupData() {
        Make make = new Make(name: "BMW").save(flsuh: true)

        new Model(name: "i3", make: make).save(flush: true, failOnError: true)
        Model model = new Model(name: "318i", make: make).save(flush: true, failOnError: true)
        model.id
    }

    void "test get"() {
        setupData()

        expect:
        modelService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Model> modelList = modelService.list(max: 2, offset: 2)

        then:
        modelList.size() == 2
        //assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        modelService.count() == 5
    }

    void "test delete"() {
        Long modelId = setupData()

        expect:
        modelService.count() == 5

        when:
        modelService.delete(modelId)
        sessionFactory.currentSession.flush()

        then:
        modelService.count() == 4
    }

    void "test save"() {
        when:
        Make make = new Make(name: "Ford").save(flush: true)
        Model model = new Model(name: "Fiesta", make: make)
        modelService.save(model)

        then:
        model.id != null
    }
}
