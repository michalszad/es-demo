package es.template.esdemo.adapters.handlers

import es.template.esdemo.domain.AggregateRepository

import es.template.esdemo.domain.Payment

class CompleteCommandHandler(private val paymentRepository: AggregateRepository<Payment>) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as CompletePaymentCommand

        val payment = paymentRepository.findById(command.id) ?: throw RuntimeException("Error")
        payment.complete()
        paymentRepository.store(payment)


    }
}
