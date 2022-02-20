package es.template.esdemo.adapters.handlers

import es.template.domain.AggregateRepository
import es.template.esdemo.domain.CompletePaymentCommand

import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentCommand

class CompleteCommandHandler(private val paymentRepository: AggregateRepository<Payment>) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as CompletePaymentCommand

        val payment = paymentRepository.findById(command.id) ?: throw RuntimeException("Error")
        payment.complete()
        paymentRepository.store(payment)


    }
}
