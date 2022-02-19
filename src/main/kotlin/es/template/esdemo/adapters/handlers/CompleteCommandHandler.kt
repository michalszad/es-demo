package es.template.esdemo.adapters.handlers

import es.template.domain.AggregateRepository
import es.template.domain.CompletePaymentCommand
import es.template.domain.PaymentCommand
import es.template.esdemo.domain.Payment

class CompleteCommandHandler(private val paymentRepository: AggregateRepository<Payment>) : CommandHandler<PaymentCommand> {

    override fun handle(command: PaymentCommand) {
        command as CompletePaymentCommand

        val payment = paymentRepository.findById(command.id) ?: throw RuntimeException("Error")
        payment.complete(command)
        paymentRepository.store(payment)


    }
}
