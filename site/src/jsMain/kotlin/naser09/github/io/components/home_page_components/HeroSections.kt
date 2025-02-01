package naser09.github.io.components.home_page_components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.css.functions.invert
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import kotlinx.browser.window
import naser09.github.io.components.data_layer.DataStore
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLAnchorElement

@Composable
fun HeroSection(colorMode: ColorMode) {
    val breakpoint = rememberBreakpoint()
    val isMobile = breakpoint < Breakpoint.MD
    var isLoading by remember { mutableStateOf(false) }

    // Function to handle PDF download
    fun downloadPDF() {
        isLoading = true
        try {
            // Create a hidden anchor element
            val link = document.createElement("a") as HTMLAnchorElement
            link.href = DataStore.RESUME_PDF
            link.download = "abu naser's resume.pdf"
            link.target = "_blank"
            document.body?.appendChild(link)
            link.click()
            document.body?.removeChild(link)
        } catch (e: Exception) {
            console.error("Error downloading PDF: $e")
            window.alert("Error downloading PDF. Please try again later.")
        } finally {
            isLoading = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .padding(top = 3.vh)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else Color.rgb(250, 250, 250))
            .padding(leftRight = if (isMobile) 16.px else 32.px),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .maxWidth(800.px)
                .margin(leftRight = autoLength)
                .gap(if (isMobile) 24.px else 32.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(if (isMobile) 150.px else 200.px)
                    .borderRadius(50.percent)
                    .overflow(Overflow.Hidden)
                    .border(
                        width = 4.px,
                        style = LineStyle.Solid,
                        color = if (colorMode == ColorMode.DARK) org.jetbrains.compose.web.css.Color.green else
                        org.jetbrains.compose.web.css.Color.greenyellow
                    )
                    .boxShadow(
                        offsetX = 0.px,
                        offsetY = 4.px,
                        blurRadius = 20.px,
                        color = Color.rgba(0, 0, 0, 0.1f)
                    )
            ) {
                Image(
                    src = "icons/profile.webp",
                    modifier = Modifier
                        .fillMaxSize()
                        .objectFit(ObjectFit.Cover)
                        .styleModifier {
                            brightness(1)
                            invert(1)
                        }
                )
            }

            // Text Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(12.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                H1(
                    attrs = Modifier
                        .margin(0.px)
                        .fontSize(if (isMobile) 2.em else 2.5.em)
                        .fontWeight(FontWeight.Bold)
                        .color(if (colorMode == ColorMode.DARK) Color.rgb(255, 255, 255) else Color.rgb(23, 23, 23))
                        .toAttrs()
                ) { Text("Abu Naser") }

                H3(
                    attrs = Modifier
                        .margin(0.px)
                        .fontSize(if (isMobile) 1.2.em else 1.5.em)
                        .textAlign(TextAlign.Center)
                        .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(75, 75, 75))
                        .toAttrs()
                ) { Text("Kotlin Developer | Tech Enthusiast") }

                P(
                    attrs = Modifier
                        .margin(0.px)
                        .maxWidth(600.px)
                        .fontSize(if (isMobile) 1.em else 1.1.em)
                        .lineHeight(1.6)
                        .textAlign(TextAlign.Center)
                        .color(if (colorMode == ColorMode.DARK) Color.rgb(180, 180, 180) else Color.rgb(90, 90, 90))
                        .toAttrs()
                ) {
                    Text("Self-Taught Kotlin Multiplatform Developer Crafting Apps for Android, iOS, Web, and Beyond")
                }
            }

            // Main Action Button
            Button(
                attrs = Modifier
                    .margin(top = 16.px)
                    .padding(leftRight = 24.px, topBottom = 12.px)
                    .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(255, 69, 0) else Color.rgb(220, 38, 38))
                    .color(Color.rgb(255, 255, 255))
                    .borderRadius(8.px)
                    .border(0.px)
                    .cursor(Cursor.Pointer)
                    .fontSize(if (isMobile) 1.em else 1.1.em)
                    .fontWeight(FontWeight.Medium)
                    .transition(Transition.of("all", 0.3.s))
                    .onClick {
                        downloadPDF()
                    }
                    .toAttrs()
            ) {
                if (isLoading){
                    Text("Downloading...")
                }else{
                    Text("Download Resume")
                }
            }

            // Social Media Buttons
            Row(
                modifier = Modifier
                    .margin(top = if (isMobile) 24.px else 32.px)
                    .gap(16.px),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                socialLinks.forEach { (url, icon) ->
                    Button(
                        attrs = Modifier
                            .backgroundColor(BackgroundColor.Transparent)
                            .border(0.px)
                            .padding(8.px)
                            .cursor(Cursor.Pointer)
                            .transition(Transition.of("transform", 0.2.s))
                            .onClick { window.open(url, "_blank") }
                            .toAttrs()
                    ) {
                        icon()
                    }
                }
            }
        }
    }
}

private val socialLinks = listOf<Pair<String, @Composable () -> Unit>>(
    "https://github.com/naser09" to {
        Image(
            src = "https://www.svgrepo.com/show/439171/github.svg",
            alt = "GitHub",
            modifier = Modifier.size(32.px)
        )
    },
    "https://www.linkedin.com/in/abu-naser-bd/" to {
        Image(
            src = "https://www.svgrepo.com/show/382726/linkedin-linked-in.svg",
            alt = "LinkedIn",
            modifier = Modifier.size(32.px)
        )
    },
    "https://facebook.com/naser100" to {
        Image(
            src = "https://www.svgrepo.com/show/349359/facebook.svg",
            alt = "Facebook",
            modifier = Modifier.size(32.px)
        )
    }
)