package es.template.esdemo.adapters.handlers

import es.template.esdemo.domain.Command

interface CommandHandler<T : Command> {
    fun handle(command: T)
}

enum class PaymentCommands {
    CREATE,
    INITIALIZE,
    COMPLETE
}
