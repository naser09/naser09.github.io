package naser09.github.io.components.auto_typing_terminal

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.focus
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.selectors.outOfRange
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.aquamarine
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.green
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.FocusEvent
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
            .fillMaxWidth()
            .width(90.vw)
            .margin(leftRight = 5.vw)
            .backgroundColor(black)
            .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
            .color(green)
            .border(2.px, LineStyle.Solid, green)
            .padding(8.px)
            .fontFamily("Courier New, monospace")
            .fontSize(18.px)
            .height(60.vh)
            .overflow(Overflow.Auto)
            .whiteSpace(WhiteSpace.PreWrap)
            .position(Position.Relative)
    }
    (Breakpoint.MD){
        Modifier
            .fillMaxWidth()
            .width(40.vw)
            .margin(leftRight = 5.vw)
            .backgroundColor(black)
            .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
            .color(green)
            .border(2.px, LineStyle.Solid, green)
            .padding(16.px)
            .fontFamily("Courier New, monospace")
            .fontSize(18.px)
            .height(40.vh)
            .overflow(Overflow.Auto)
            .whiteSpace(WhiteSpace.PreWrap)
            .position(Position.Relative)
            .resize(Resize.Vertical)
    }
    hover {
        Modifier.cursor(Cursor.Text)
    }
}

val InputMobileStyle = CssStyle {
    base {
        Modifier.fontSize(FontSize.Medium)
        .padding(0.px) // Remove padding
        .margin(0.px) // Remove margin
        .border(0.px) // Remove border
        .outline(0.px) // Remove outline
        .backgroundColor(BackgroundColor.Transparent) // Transparent background
        .color(green) // Text
        .styleModifier {
            property("outline", "none")
            property("border", "none")
            property("-webkit-appearance","none")
            property("box-shadow","none")
        }
    }
    focus{
        Modifier
            .padding(0.px) // Remove padding
            .margin(0.px) // Remove margin
            .border(0.px) // Remove border
            .outline(0.px)
            .backgroundColor(BackgroundColor.Transparent) // Transparent background
            .color(green) // Text
            .styleModifier {
                property("outline", "none")
                property("border", "none")
                property("-webkit-appearance","none")
                property("box-shadow","none")
            }
    }
    focusVisible{
        Modifier
            .padding(0.px) // Remove padding
            .margin(0.px) // Remove margin
            .border(0.px) // Remove border
            .outline(0.px)
            .backgroundColor(BackgroundColor.Transparent) // Transparent background
            .color(green) // Text
            .styleModifier {
                property("outline", "none")
                property("border", "none")
                property("-webkit-appearance","none")
                property("appearance","none")
                property("box-shadow","none")
            }
    }
}
@Composable
fun AutoTypingTerminal() {
    DisposableEffect(Unit){
        window.addEventListener("DOMContentLoaded",{event: Event ->
            val element = document.getElementById("terminal_container")
            if (element==null){
                console.log("null element")
                return@addEventListener
            }
            val op = IntersectionObserver.Options(thresholds = listOf(0.9))
            val observer = IntersectionObserver(op) { entries ->
                if (entries.any { it.isIntersecting }){
                    val input = document.getElementById("input") as HTMLInputElement
                    input.focus()
                    window.focus()
                }
            }
            observer.observe(element)
            onDispose { observer.disconnect() }
        })
        onDispose {

        }
    }
    var displayedText by remember { mutableStateOf("") }
    val fullText = "Welcome to my portfolio! I'm a passionate self-taught Kotlin Multiplatform developer with a knack for building cross-platform apps that work seamlessly on Android, iOS, web, and desktop.\n" +
            "\n" +
            "Here, you’ll find projects that showcase my journey, skills, and dedication to creating efficient, user-friendly, and visually appealing applications. Whether it’s crafting intuitive UIs, optimizing backend logic, or exploring new technologies, I’m always eager to learn and improve.\n" +
            "\n" +
            "Feel free to explore my work and reach out if you’d like to collaborate or learn more about what I do!"
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
            "help" to "Available commands: help, clear, about, projects , hi/hello ",
            "hi" to fullText,
            "hello" to fullText,
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
            delay(30)
            scrollToBottom()
        }
        terminalState = terminalState.copy(
            outputs = listOf(Pair(TerminalCommand("Booting....."),welcomeOutput))
        )
        isTypingDone = true
        delay(300)
        scrollToBottom()
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
        val breakpoint = rememberBreakpoint()
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

                    // ++++++++++++++++++++  mobile ---------
//                    Input(InputType.Text, attrs = Modifier
//                        .backgroundColor(BackgroundColor.Transparent)
//                        .id("input")
//                        .toAttrs {
//                            onInput {
//                                userInput += it.value
//                            }
//                        })
                    //+++++++++++++++++++++++++++=
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
                    if (breakpoint>=Breakpoint.MD){
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
                    }else{
                        Span {
                            Text(directoryPrompt)
                            Input(InputType.Text, InputMobileStyle.toAttrs {
                                    onInput { userInput = it.value }
                                })
                        }
                    }

                }
            }
        }
    }
}