package es.template.esdemo.adapters.handlers

import es.template.domain.Command
import es.template.domain.PaymentCommand
import es.template.esdemo.adapters.db.InMemoryPaymentRepository
import es.template.esdemo.adapters.psp.TestPaymentProviderService
import es.template.esdemo.infrastructure.InMemoryBasedEventStore

interface CommandHandler<T : Command> {
    fun handle(command: T)
}

enum class PaymentCommands {
    CREATE,
    INITIALIZE,
    COMPLETE
}

fun paymentCommandHandlers(): Map<PaymentCommands, CommandHandler<PaymentCommand>> {
    val eventStore = InMemoryBasedEventStore()
    val repository = InMemoryPaymentRepository(eventStore)
    val paymentProvider = TestPaymentProviderService()

    return mapOf(
        PaymentCommands.CREATE to CreateCommandHandler(repository),
        PaymentCommands.INITIALIZE to InitializeCommandHandler(repository, paymentProvider),
        PaymentCommands.COMPLETE to CompleteCommandHandler(repository)
    )
}
