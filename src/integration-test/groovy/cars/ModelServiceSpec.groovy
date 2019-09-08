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
        Long modelId = setupData()

        expect:
        modelService.get(modelId) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Model> modelList = modelService.list(max: 2)

        then:
        modelList.size() == 2
    }

    void "test count"() {
        setupData()

        expect:
        modelService.count() > 0
    }

    void "test delete"() {
        Long modelId = setupData()

        int numOfModels = modelService.count()

        when:
        modelService.delete(modelId)
        sessionFactory.currentSession.flush()

        then:
        modelService.count() == numOfModels - 1
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
