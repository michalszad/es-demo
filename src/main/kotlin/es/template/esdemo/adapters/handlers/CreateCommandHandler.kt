package es.template.esdemo.adapters.handlers

import es.template.domain.AggregateRepository
import es.template.esdemo.domain.CreatePaymentCommand

import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentCommand

class CreateCommandHandler(private val paymentRepository: AggregateRepository<Payment>) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as CreatePaymentCommand

        val payment = Payment.create(command.description)
        paymentRepository.store(payment)
    }
}
