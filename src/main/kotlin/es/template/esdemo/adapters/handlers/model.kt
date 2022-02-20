package es.template.esdemo.adapters.handlers

interface Command

interface PaymentCommand : Command

data class CreatePaymentCommand(val paymentId: String, val description: String) : PaymentCommand
data class InitializePaymentCommand(val id: String) : PaymentCommand
data class CompletePaymentCommand(val id: String) : PaymentCommand


interface CommandHandler<T : Command> {
    fun handle(command: T)
}

enum class PaymentCommands {
    CREATE,
    INITIALIZE,
    COMPLETE
}
