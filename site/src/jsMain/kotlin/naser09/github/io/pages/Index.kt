package naser09.github.io.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.css.functions.invert
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.graphics.Canvas2d
import com.varabyte.kobweb.silk.components.icons.fa.FaFacebook
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaLinkedin
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.window
import kotlinx.coroutines.delay
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.SocialButtonStyle
import naser09.github.io.components.auto_typing_terminal.AutoTypingTerminal
import naser09.github.io.components.neumorphism.GlassMorphismButton
import naser09.github.io.components.neumorphism.GlassMorphismCardStyle
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.green

@Page
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState
    var scrolled by remember { mutableStateOf(false) }
    val breakpoint = rememberBreakpoint()
    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(colorMode.toSitePalette().nearBackground)
                .color(if (colorMode == ColorMode.DARK) white else black)
                .onMouseMove { scrolled = true }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().maxWidth(1200.px).margin { auto }
                    .padding(24.px)
            ) {

                if (breakpoint>Breakpoint.MD){
                    Row(Modifier.width(100.vw),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Box(Modifier.width(50.vw)){
                            // Hero Section with responsive layout
                            HeroSection(colorMode)
                        }
                        Box(Modifier.width(50.vw)){
                            // Tech Stack
                            TechStackSection()
                        }
                    }
                }else{
                    // Hero Section with responsive layout
                    HeroSection(colorMode)

                    // Tech Stack
                    TechStackSection()
                }

                // Timeline
                TimelineSection(colorMode, scrolled)

                // Footer
                Footer(colorMode)

                Box(Modifier.height(10.vh)){
                    P {
                        Text("")
                    }
                }
            }

            // Scroll Indicator
            //ScrollIndicator(colorMode)
        }
    }
}


@Composable
private fun SocialMediaButton() {
    val breakpoint = rememberBreakpoint()
    val size = when(breakpoint){
        Breakpoint.ZERO -> IconSize.SM
        Breakpoint.SM -> IconSize.SM
        Breakpoint.MD -> IconSize.LG
        Breakpoint.LG -> IconSize.XL
        Breakpoint.XL -> IconSize.XXL
    }
    val socialLinks = listOf<Pair<String , @Composable ()->Unit>>(
        "https://github.com/naser09" to { FaGithub(size = size) },
        "https://linkedin.com/in/naser100" to { FaLinkedin(size = size) },
        "https://facebook.com/naser100" to { FaFacebook(size = size) }
    )
    // Social Media Buttons
    Row(
        modifier = Modifier
            .aspectRatio(2/3),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        socialLinks.forEach { (url, icon) ->
            Button(
                attrs = SocialButtonStyle.toModifier()
                    .onClick {
                        window.open(url, "_blank")
                    }
                    .toAttrs()
            ) {
                icon()
            }
        }
    }
}
@Composable
private fun ScrollIndicator(colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .position(Position.Fixed)
            .bottom(24.px)
            .left(50.percent)
            .transform { translateX((-50).percent) }
            .padding(8.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(245, 245, 245))
            .borderRadius(24.px)
            .opacity(70)
    ) {
        Box(
            modifier = Modifier
                .size(24.px)
                .border(1.px, LineStyle.Solid, if (colorMode == ColorMode.DARK) white else black)
                .borderRadius(50.percent)
                .animation(
//s
                )
                .transform { translateY(8.px) }
        )
    }
}

@Composable
private fun HeroSection(colorMode: ColorMode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .margin(top = 48.px)
            .gap(32.px),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.px)
                .borderRadius(50.percent)
                .backgroundColor(Color.rgb(40, 40, 40))
                .overflow(Overflow.Hidden)
        ) {
            Image(
                src = "https://avatars.githubusercontent.com/u/75668297?v=4",
                modifier = Modifier
                    .fillMaxSize()
                    .objectFit(ObjectFit.Cover)
                    .styleModifier {
                        brightness(1)
                        invert(1)
                    }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().gap(16.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            H1(
                attrs = Modifier
                    .fontSize(48.px)
                    .fontWeight(700)
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Abu Naser") }

            P(
                attrs = Modifier
                    .fontSize(24.px)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(60, 60, 60))
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Kotlin Developer | Tech Enthusiast") }

            SocialMediaButton()
        }
    }
}

@Composable
private fun TimelineItem(
    year: String,
    description: String,
    icon: String,
    colorMode: ColorMode,
    visible: Boolean
) {
    Box(modifier = Modifier.width(70.vw)
        .height(90.vh)
        .padding(topBottom = 5.vh)
        .then(GlassMorphismCardStyle.toModifier())){
        Column {
            H2 { Text(year) }
            H4 {
                Text(description)
            }
        }
    }
}
@Composable
private fun TimelineItem2(
    year: String,
    description: String,
    icon: String,
    colorMode: ColorMode,
    visible: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .padding(topBottom = 48.px)
            .opacity(if (visible) 100 else 0)
            .transform { translateY(if (visible) 0.px else 50.px) }
//            .transition(Transition.group {
//                transform(500.ms)
//                opacity(500.ms)
//            })
        ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .maxWidth(800.px)
                .padding(24.px)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
                .borderRadius(8.px)
        ) {
            Column(
                modifier = Modifier.gap(24.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    src = "/icons/$icon.svg",
                    modifier = Modifier.size(64.px)
                )

                H3(
                    attrs = Modifier
                        .fontSize(32.px)
                        .margin(0.px)
                        .textAlign(TextAlign.Center)
                        .toAttrs()
                ) { Text(year) }

                P(
                    attrs = Modifier
                        .fontSize(20.px)
                        .margin(0.px)
                        .textAlign(TextAlign.Center)
                        .toAttrs()
                ) { Text(description) }
            }
        }
    }
}

@Composable
private fun TimelineSection(colorMode: ColorMode, scrolled: Boolean) {
    val timelineItems = listOf(
        Triple("2017", "Started self-learning Java and Android development", "android"),
        Triple("2019", "Transitioned to Kotlin and XML for Android apps", "kotlin"),
        Triple("2020", "Adopted Jetpack Compose and published two Play Store apps", "jetpack"),
        Triple("2021", "Began self-learning Kotlin Multiplatform and started Naser's Codelab on YouTube", "youtube"),
        Triple("2022-Present", "Built backend system using Ktor, Exposed, Redis with Kobweb admin panel", "backend")
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        timelineItems.forEachIndexed { index, (year, description, icon) ->
            TimelineItem(year, description, icon, colorMode, scrolled)
        }
    }
}

@Composable
private fun TechStackSection() {

    val techStack = listOf(
        "kotlin" to "Kotlin",
        "jetpack" to "Jetpack Compose",
        "ktor" to "Ktor",
        "redis" to "Redis",
        "graphql" to "GraphQL"
    )
    Column {
        AutoTypingTerminal()
        SimpleGrid(
            modifier = Modifier.fillMaxWidth().margin(top = 48.px),
            numColumns = numColumns(base = 2, sm = 3, md = 5)
        ) {
            techStack.forEach { (icon, name) ->
                Box(
                    modifier = Modifier
                        .padding(16.px)
                        .transition(Transition.of("transform",300.ms))
                        .styleModifier {
                            CssStyle {
                                hover{
                                    Modifier.transform {
                                        scale(1.1)
                                    }
                                }
                            }
                        }
//                    .transition(CSSTransition("transform", 300.ms))
//                    .hover { transform { scale(1.1) } }
                ) {
                    Column {
                        GlassMorphismButton(Modifier.size(30.px,20.px),{}){
                            Text(name)
                        }
                        Image(
                            src = "/icons/$icon.svg",
                            modifier = Modifier.size(48.px)
                        )
                        Span(
                            attrs = Modifier
                                .fontSize(14.px)
                                .margin(top = 8.px)
                                .toAttrs()
                        ) { Text(name) }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineItem(
    year: String,
    description: String,
    icon: String,
    isLeft: Boolean,
    colorMode: ColorMode,
    visible: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = 32.px)
            .opacity(if (visible) 100 else 0)
            .transform { translateY(if (visible) 0.px else 50.px) }
            .transition(
                Transition.group(listOf("transform","opacity"),500.ms)
            )
//            .transition(Transition.group {
//                transform(500.ms)
//                opacity(500.ms)
//            })
        ,
        horizontalArrangement = if (isLeft) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .maxWidth(500.px)
                .padding(24.px)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
                .borderRadius(8.px)
        ) {
            Row(
                modifier = Modifier.gap(16.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    src = "/icons/$icon.svg",
                    modifier = Modifier.size(32.px)
                )
                Column(modifier = Modifier.gap(8.px)) {
                    H3(
                        attrs = Modifier
                            .fontSize(20.px)
                            .margin(0.px)
                            .toAttrs()
                    ) { Text(year) }
                    P(
                        attrs = Modifier
                            .fontSize(16.px)
                            .margin(0.px)
                            .toAttrs()
                    ) { Text(description) }
                }
            }
        }
    }
}
@Composable
private fun Footer(colorMode: ColorMode) {
    val socialLinks = listOf(
        "github" to "https://github.com/username",
        "youtube" to "https://youtube.com/@nasers.codelab",
        "linkedin" to "https://linkedin.com/in/username"
    )

    Footer(
        attrs = Modifier
            .fillMaxWidth()
            .margin(top = 96.px, bottom = 48.px)
            .padding(top = 48.px)
            .borderTop(1.px, LineStyle.Solid, if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(230, 230, 230))
            .toAttrs()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation links
            Row(modifier = Modifier.gap(24.px)) {
                listOf("About", "Projects", "YouTube", "Contact").forEach { link ->
                    A(
                        href = "/$link".lowercase(),
                        attrs = Modifier
                            .color(if (colorMode == ColorMode.DARK) white else black)
                            .textDecorationLine(TextDecorationLine.None)
                            .styleModifier {
                                CssStyle {
                                    hover{
                                        Modifier.color(Color.rgb(100,100,100))
                                    }
                                }
                            }
//                            .hover { color(Color.rgb(100, 100, 100)) }
                            .transition(Transition.of("color",300.ms))
//                            .transition(CSSTransition("color", 300.ms))
                            .toAttrs()
                    ) { Text(link) }
                }
            }

            // Social media icons
            Row(modifier = Modifier.gap(16.px)) {
                socialLinks.forEach { (platform, url) ->
                    A(
                        href = url,
                        attrs = Modifier
                            .color(if (colorMode == ColorMode.DARK) white else black)
                            .styleModifier {
                                CssStyle {
                                    hover{
                                        Modifier.color(Color.rgb(100,100,100))
                                    }
                                }
                            }
//                            .hover { color(Color.rgb(100, 100, 100)) }
                            .transition(Transition.of("color",300.ms))
//                            .transition(CSSTransition("color", 300.ms))
                            .toAttrs()
                    ) {
                        Image(
                            src = "/icons/$platform.svg",
                            modifier = Modifier.size(24.px)
                        )
                    }
                }
            }
        }
    }
}
