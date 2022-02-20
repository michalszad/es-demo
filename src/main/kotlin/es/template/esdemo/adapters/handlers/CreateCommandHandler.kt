package es.template.esdemo.adapters.handlers

import es.template.esdemo.domain.AggregateRepository

import es.template.esdemo.domain.Payment

class CreateCommandHandler(private val paymentRepository: AggregateRepository<Payment>) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as CreatePaymentCommand

        val payment = Payment.create(command.paymentId, command.description)
        paymentRepository.store(payment)
    }
}
