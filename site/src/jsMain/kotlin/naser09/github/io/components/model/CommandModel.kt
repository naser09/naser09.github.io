package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class Command(
    val command: String,
    val description: String,
    val category: String = "general",
    val arguments: List<String> = emptyList(),
    val examples: List<String> = emptyList(),
    val response: String = "",
    val errorResponse: String = "Command failed"
)
object CommandHelper{
    fun executeCommand(commands:Map<String,Command>,input: String): String {
        val parts = input.trim().split("\\s+".toRegex())
        val commandName = parts[0].lowercase()
        val args = if (parts.size > 1) parts.subList(1, parts.size) else emptyList()

        return when (commandName) {
            "help" -> handleHelp(commands,args)
            else -> {
                val command = commands[commandName] ?: return "Command not found: $commandName"
                when (commandName) {
                    "echo" -> args.joinToString(" ")
                    else -> command.response
                }
            }
        }
    }
    suspend fun executeCommand(
        commands:Map<String,Command>,
        input: String,
        animate:suspend (command: Command,args:List<String>)->Unit): String {
        val parts = input.trim().split("\\s+".toRegex())
        val commandName = parts[0].lowercase()
        val args = if (parts.size > 1) parts.subList(1, parts.size) else emptyList()

        return when (commandName) {
            "help" -> handleHelp(commands,args)
            else -> {
                val command = commands[commandName] ?: return "Command not found: $commandName"
                if (command.category =="data"){
                    animate(command,args)
                    return  ""
                }
                when (commandName) {
                    "echo" -> args.joinToString(" ")
                    else -> command.response
                }
            }
        }
    }
    private fun handleHelp(commands:Map<String,Command>,args: List<String>): String {
        if (args.isEmpty()) {
            return generateGeneralHelp(commands)
        }
        val commandName = args[0]
        val command = commands[commandName] ?: return "No help available for: $commandName"
        return generateCommandHelp(command)
    }
    private fun generateGeneralHelp(commands:Map<String,Command>): String {
        return buildString {
            appendLine("╔════ Terminal Command Help ════╗")
            appendLine("║                              ║")

            // Group commands by category
            val categorizedCommands = commands.values.groupBy { it.category }

            categorizedCommands.forEach { (category, cmds) ->
                appendLine("║ ${category.uppercase()}:")
                cmds.forEach { cmd ->
                    appendLine("║  ${cmd.command.padEnd(10)} │ ${cmd.description}")
                }
                appendLine("║")
            }

            appendLine("║ For detailed help on any command:")
            appendLine("║  help <command>")
            appendLine("║")
            appendLine("║ To see commands by category:")
            appendLine("║  help --category <category>")
            appendLine("╚══════════════════════════════╝")
        }
    }


    private fun generateCommandHelp(command: Command): String {
        return buildString {
            appendLine("╔════ Command: ${command.command.uppercase()} ════╗")
            appendLine("║")
            appendLine("║ Description:")
            appendLine("║   ${command.description}")
            appendLine("║")
            if (command.arguments.isNotEmpty()) {
                appendLine("║ Arguments:")
                command.arguments.forEach { arg ->
                    appendLine("║   $arg")
                }
                appendLine("║")
            }
            if (command.examples.isNotEmpty()) {
                appendLine("║ Examples:")
                command.examples.forEach { example ->
                    appendLine("║   $ $example")
                }
                appendLine("║")
            }
            appendLine("║ Category: ${command.category}")
            appendLine("╚══════════════════════════════╝")
        }
    }
}