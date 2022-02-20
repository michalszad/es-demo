package es.template.esdemo.adapters.handlers

import es.template.esdemo.domain.AggregateRepository
import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentExternalService

class InitializeCommandHandler(
    private val paymentRepository: AggregateRepository<Payment>,
    private val paymentExternalService: PaymentExternalService,
) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as InitializePaymentCommand

        val payment = paymentRepository.findById(command.id) ?: paymentRepository.create()
        payment.initialize()
        paymentRepository.store(payment)
    }
}
