package naser09.github.io.components.auto_typing_terminal

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.green
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.KeyboardEvent

// Data classes for structured output
data class TerminalCommand(
    val command: String,
    val timestamp: Long = window.performance.now().toLong()
)

data class TerminalOutput(
    val command: String,
    val content: String,
    val type: OutputType
)

enum class OutputType {
    WELCOME,
    COMMAND,
    RESPONSE,
    ERROR
}

data class TerminalState(
    val outputs: List<Pair<TerminalCommand,TerminalOutput>> = emptyList()
)

val TerminalStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(black)
            .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
            .color(green)
            .border(2.px, LineStyle.Solid, green)
            .padding(16.px)
            .fontFamily("Courier New, monospace")
            .fontSize(18.px)
            .width(80.percent)
            .height(200.px)
            .overflow(Overflow.Auto)
            .whiteSpace(WhiteSpace.PreWrap)
            .position(Position.Relative)
            .resize(Resize.Both)
    }
    hover {
        Modifier.cursor(Cursor.Text)
    }
}

@Composable
fun AutoTypingTerminal() {
    var displayedText by remember { mutableStateOf("") }
    val fullText = "Welcome to My Portfolio!\nExplore my projects and journey in tech.\n type 'help' commands to see available commands."
    val directoryPrompt = "Root@Naser:~> "
    var cursorVisible by remember { mutableStateOf(true) }
    var isTypingDone by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var terminalState by remember { mutableStateOf(TerminalState()) }
    var animatingOutput by remember { mutableStateOf<TerminalOutput?>(null) }
    var currentAnimatedText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Define available commands
    val commands = remember {
        mapOf(
            "help" to "Available commands: help, clear, about, projects",
            "about" to "I'm a software developer passionate about Kotlin and Android development.",
            "projects" to "1. Project A - Android App\n2. Project B - Web Application",
            "clear" to "CLEAR_COMMAND"
        )
    }

    // Auto-scroll function
    fun scrollToBottom() {
        val terminalElement = document.getElementById("terminal_container")
        terminalElement?.let { element ->
            element.scrollTop = element.scrollHeight.toDouble()
        }
    }

    // Animate text output function
    suspend fun animateText(command: TerminalCommand,output: TerminalOutput) {
        animatingOutput = output
        currentAnimatedText = ""
        for (i in output.content.indices) {
            currentAnimatedText = output.content.substring(0, i + 1)
            delay(30)
            scrollToBottom()
        }
        // Add the completed output to terminal state
        terminalState = terminalState.copy(
            outputs = terminalState.outputs + Pair(command,output)
        )
        animatingOutput = null
        currentAnimatedText = ""
        delay(300)
        scrollToBottom()
    }

    // Initial typing animation
    LaunchedEffect(Unit) {
        val welcomeOutput = TerminalOutput("Booting.....",fullText, OutputType.WELCOME)
        for (i in welcomeOutput.content.indices) {
            displayedText ="Booting.....\n"+ welcomeOutput.content.substring(0, i + 1)
            delay(100)
            scrollToBottom()
        }
        terminalState = terminalState.copy(
            outputs = listOf(Pair(TerminalCommand("Booting....."),welcomeOutput))
        )
        isTypingDone = true
    }

    // Cursor blinking effect
    LaunchedEffect(isTypingDone) {
        while (true) {
            delay(500)
            cursorVisible = !cursorVisible
        }
    }

    // Handle keyboard input
    DisposableEffect(Unit) {
        val keyboardListener = object :EventListener{
            override fun handleEvent(event: Event) {
                val keyboardEvent = event as KeyboardEvent
                if (isTypingDone && animatingOutput == null) {
                    when (keyboardEvent.key) {
                        "Enter" -> {
                            if (userInput.isNotEmpty()) {
                                val command = userInput.trim().lowercase()
                                val commandOutput = TerminalCommand(userInput)

                                when {
                                    command == "clear" -> {
                                        terminalState = TerminalState()
                                    }
                                    commands.containsKey(command) -> {
                                        val output = commands[command] ?: ""
                                        scope.launch {
//                                            terminalState = terminalState.copy(
//                                                commands = terminalState.commands + commandOutput
//                                            )
//
                                            animateText(commandOutput,TerminalOutput(commandOutput.command,output, OutputType.RESPONSE))
                                        }
                                    }
                                    else -> {
                                        scope.launch {
//                                            terminalState = terminalState.copy(
//                                                commands = terminalState.commands + commandOutput
//                                            )
                                            animateText(commandOutput,TerminalOutput(commandOutput.command,"Command not found: $command", OutputType.ERROR))
                                        }
                                    }
                                }
                                userInput = ""
                            }
                        }
                        "Backspace" -> {
                            if (userInput.isNotEmpty()) {
                                userInput = userInput.dropLast(1)
                            }
                        }
                        else -> {
                            if (keyboardEvent.key.length == 1) {
                                userInput += keyboardEvent.key
                            }
                        }
                    }
                }
            }
        }

        window.addEventListener("keydown", keyboardListener)
        onDispose {
            window.removeEventListener("keydown", keyboardListener)
        }
    }

    Box(
        TerminalStyle.toModifier()
            .id("terminal_container")
            .onClick {
                // Focus handling can be added here if needed
            }
    ) {
        Div(attrs = Modifier
            .fillMaxSize()
            .padding(8.px)
            .toAttrs()
        ) {
            // Display initial welcome message or previous outputs
            if (!isTypingDone) {
                Span {
                    Text(directoryPrompt + displayedText)
                }
                Span(
                    Modifier
                        .width(10.px)
                        .toAttrs()
                ) {
                    if (cursorVisible) {
                        Text("┃")
                    }
                }
            }

            // Display terminal history
            terminalState.outputs.forEach { output ->
                Div {
                    Text(directoryPrompt + output.first.command +"\n")
                    Text(output.second.content)
//                    if (output.type == OutputType.COMMAND) {
//                    } else {
//                        Text(output.content)
//                    }
                }
            }

            // Display currently animating output
            animatingOutput?.let { output ->
                Div {
                    Text(directoryPrompt+ output.command +"\n")
                    Text(currentAnimatedText)
                }
            }

            // Display current input line
            if (isTypingDone && animatingOutput == null) {
                Div {
                    Text(directoryPrompt + userInput)
                    Span(
                        Modifier
                            .width(10.px)
                            .toAttrs()
                    ) {
                        if (cursorVisible) {
                            Text("┃")
                        }
                    }
                }
            }
        }
    }
}