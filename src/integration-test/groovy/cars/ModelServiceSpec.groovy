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
        // TODO: Populate valid domain instances and return a valid ID
        //new Model(...).save(flush: true, failOnError: true)
        //new Model(...).save(flush: true, failOnError: true)
        //Model model = new Model(...).save(flush: true, failOnError: true)
        //new Model(...).save(flush: true, failOnError: true)
        //new Model(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //model.id
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
        assert false, "TODO: Verify the correct instances are returned"
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
        assert false, "TODO: Provide a valid instance to save"
        Model model = new Model()
        modelService.save(model)

        then:
        model.id != null
    }
}
