package naser09.github.io.components.auto_typing_terminal

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.focus
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import naser09.github.io.components.data_layer.DataStore
import naser09.github.io.components.model.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.green
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
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
            .fillMaxWidth()
            .width(90.vw)
            .margin(leftRight = 5.vw)
            .backgroundColor(black)
            .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
            .color(green)
            .border(2.px, LineStyle.Solid, green)
            .padding(8.px)
            .fontFamily("Courier New","monospace")
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
            .fontFamily("Courier New","monospace")
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
    val fullText =  "Welcome to my portfolio! \uD83D\uDC4B\n" +
            "I'm a self-taught Kotlin & Kotlin Multiplatform developer, passionate about building cross-platform solutions. Explore my work and let's create something amazing together!"
    val directoryPrompt = "Root@Naser:~> "
    var cursorVisible by remember { mutableStateOf(true) }
    var isTypingDone by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var terminalState by remember { mutableStateOf(TerminalState()) }
    var animatingOutput by remember { mutableStateOf<TerminalOutput?>(null) }
    var currentAnimatedText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit){
        DataStore.loadTerminalCommands()
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
            if (output.content.get(i+1) == ' ') continue
            currentAnimatedText = output.content.substring(0, i + 1)
            if (i < 5){
                delay(10)
            }else if (i<50) {
                delay(5)
            }else delay(1)
            if (i%20==0){
                scrollToBottom()
            }
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
            delay(10)
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
    suspend fun<T> getDate(command: String,
                           serializationStrategy: SerializationStrategy<T>,
                           default:T?=null,
                           hideKeys: Boolean = true,
                           useNumbering: Boolean = false,
                           indentSize: Int = 4,
                           itemPerLine:Int = 0,
                           loader:suspend ()->T){
        var data = default
        animatingOutput  = TerminalOutput(command,"Downloading.",OutputType.COMMAND)
        if (data==null){
            val job = scope.launch {
                data = loader()
            }
            var loading = "Downloading."
            while (job.isActive){
                currentAnimatedText = loading
                delay(300)
                scrollToBottom()
                loading+="."
            }
        }
        animateText(
            TerminalCommand(command,0),
            TerminalOutput(
                command,data?.let {
                    DataStore.prettyPrint(serializationStrategy,it,hideKeys, useNumbering, indentSize, itemPerLine)
                }?:"No data found",OutputType.COMMAND
            )
        )
    }
    val commandHistory = remember {
        mutableStateListOf<String>()
    }
    var lastCommand by remember { mutableStateOf(-1) }
    // Handle keyboard input
    DisposableEffect(Unit) {
        val keyboardListener = object :EventListener{
            override fun handleEvent(event: Event) {
                val keyboardEvent = event as KeyboardEvent
                if (isTypingDone && animatingOutput == null) {
                    when (keyboardEvent.key) {
                        "ArrowUp" ->{
                            keyboardEvent.preventDefault()
                            if (commandHistory.isEmpty()){ return }
                            if (lastCommand<0){
                                userInput = commandHistory.last()
                                lastCommand = commandHistory.lastIndex-1
                            }else{
                                userInput = commandHistory[lastCommand]
                                lastCommand -= 1
                            }
                            scrollToBottom()
                        }
                        "Enter" -> {
                            if (userInput.isNotEmpty()) {
                                val command = userInput.trim().lowercase()
                                val commandOutput = TerminalCommand(userInput)
                                commandHistory.add(command)
                                when{
                                    command == "history"->{
                                        val data = buildString {
                                            commandHistory.forEachIndexed { index, s ->
                                                append("    $index. $s \n")
                                            }
                                        }
                                        scope.launch {
                                            animateText(command = TerminalCommand(command = command,0),
                                                TerminalOutput(command = command,data,OutputType.RESPONSE)
                                            )
                                        }
                                    }
                                    command == "clear" ->{
                                        terminalState = TerminalState()
                                        userInput =""
                                    }
                                    command == "hi" || command=="hello" ->{
                                        scope.launch {
                                            animateText(
                                                TerminalCommand(command = command,0),
                                                output = TerminalOutput(command = command,fullText,OutputType.WELCOME)
                                            )
                                        }
                                    }
                                    else ->{
                                        val dataStoreCommands = DataStore.terminalCommands.value?.map { it.command to it }?.toMap()?: emptyMap()
                                        scope.launch {
                                            val result = CommandHelper.executeCommand(
                                                dataStoreCommands,
                                                command
                                            ){com ,args->
                                                console.log("args : $args")
                                                val showJsonKey = args.any { it == "-k" || it =="key" }
                                                val showNumeric = args.any { it == "-n" || it == "n"}
                                                val itemCount = args.getOrNull(args.indexOf("-i")+1)?.toIntOrNull()?:2
                                                console.log("key $showJsonKey")
                                                console.log("num $showNumeric")
                                                console.log("item $itemCount")
                                                when(com.command){
                                                    "about"->{
                                                        if (args.contains("skill")){
                                                            getDate(
                                                                command,
                                                                ListSerializer(Skill.serializer()),
                                                                DataStore.personalSkill.value,
                                                                hideKeys = !showJsonKey,
                                                                useNumbering = showNumeric,
                                                                itemPerLine = itemCount + 1
                                                            ) {
                                                                DataStore.loadPersonalSkill()
                                                                DataStore.personalSkill.value ?: emptyList()
                                                            }
                                                        }else {
                                                            getDate(
                                                                command,
                                                                ListSerializer(PersonalInfo.serializer()),
                                                                DataStore.personalInfo.value,
                                                                hideKeys = !showJsonKey,
                                                                useNumbering = showNumeric,
                                                                itemPerLine = itemCount + 1
                                                            ) {
                                                                DataStore.loadPersonalInfo()
                                                                DataStore.personalInfo.value ?: emptyList()
                                                            }
                                                        }
                                                    }
                                                    "academic"->{
                                                        getDate(
                                                            command,
                                                            ListSerializer(Academic.serializer()),
                                                            DataStore.academic.value,
                                                            hideKeys = !showJsonKey,
                                                            useNumbering = showNumeric,
                                                            itemPerLine = itemCount
                                                        ){
                                                            DataStore.loadAcademic()
                                                            DataStore.academic.value?: emptyList()
                                                        }
                                                    }
                                                    "videos" ->{
                                                        getDate(
                                                            command,
                                                            ListSerializer(VideoItem.serializer()),
                                                            DataStore.videos.value,
                                                            hideKeys = !showJsonKey,
                                                            useNumbering = showNumeric,
                                                            itemPerLine = itemCount
                                                        ){
                                                            DataStore.loadVideos()
                                                            DataStore.videos.value?: emptyList()
                                                        }
                                                    }
                                                    "stacks" ->{
                                                        getDate(
                                                            command,
                                                            ListSerializer(Technology.serializer()),
                                                            DataStore.technologies.value,
                                                            hideKeys = !showJsonKey,
                                                            useNumbering = showNumeric,
                                                            itemPerLine = itemCount
                                                        ){
                                                            DataStore.loadTechStack()
                                                            DataStore.technologies.value?: emptyList()
                                                        }
                                                    }
                                                    "projects"->{
                                                        getDate(
                                                            command,
                                                            ListSerializer(Project.serializer()),
                                                            DataStore.projects.value,
                                                            hideKeys = !showJsonKey,
                                                            useNumbering = showNumeric,
                                                            itemPerLine = itemCount
                                                        ){
                                                            DataStore.loadProjects()
                                                            DataStore.projects.value?: emptyList()
                                                        }
                                                    }
                                                }
                                            }
                                            if (result!=""){
                                                animateText(
                                                    commandOutput,
                                                    TerminalOutput(command,CommandHelper.executeCommand(dataStoreCommands,command),OutputType.COMMAND)
                                                )
                                            }
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
                            keyboardEvent.preventDefault()
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
                val input = document.getElementById("terminal_input") as? HTMLInputElement
                input?.focus()
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
                Div{
                    Text(directoryPrompt + output.first.command +"\n")
                    Text(output.second.content)
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
                        Row (Modifier.overflow(Overflow.Auto)){
                            Text(directoryPrompt)
                            Input(InputType.Text, InputMobileStyle
                                .toAttrs {
                                    id("terminal_input")
                                    onInput { userInput = it.value }
                                })
                        }
                    }

                }
            }
        }
    }
}