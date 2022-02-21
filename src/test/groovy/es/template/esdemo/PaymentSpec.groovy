package es.template.esdemo

import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentCreatedEvent
import spock.lang.Specification

class PaymentSpec extends Specification {


    def "Should add PaymentCreatedEvent"() {
        given:
        def paymentId = "test-test"
        def description = "Test description"

        when:
        def changes = Payment.create(paymentId, description).pullChanges()

        then:
        changes.size() == 1
        changes[0] instanceof PaymentCreatedEvent
    }

}
