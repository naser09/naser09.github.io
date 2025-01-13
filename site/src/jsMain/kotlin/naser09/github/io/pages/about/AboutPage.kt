package naser09.github.io.pages.about

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
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
import com.varabyte.kobweb.silk.style.selectors.hover
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black

// About Page
@Page("/about")
@Composable
fun AboutPage() {
    var colorMode by remember { mutableStateOf(ColorMode.DARK) }
    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else white)
                .color(if (colorMode == ColorMode.DARK) white else black)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                PageHeader(
                    "About Me",
                    "Get to know the person behind the code",
                    colorMode
                )

                AboutContent(colorMode)
            }
        }
    }
}

@Composable
private fun AboutContent(colorMode: ColorMode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .gap(32.px),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        // Profile photo
        Box(
            modifier = Modifier
                .size(300.px)
                .borderRadius(8.px)
                .overflow(Overflow.Hidden)
        ) {
            Image(
                src = "/profile.jpg",
                modifier = Modifier.fillMaxSize().objectFit(ObjectFit.Cover)
            )
        }

        // Details
        Column(
            modifier = Modifier.maxWidth(600.px).gap(24.px)
        ) {
            DetailCard("Personal Information", colorMode) {
                P { Text("Name: Abu Naser") }
                P { Text("Birthdate: January 1, 1999") }
            }

            DetailCard("Hobbies & Interests", colorMode) {
                listOf(
                    "Coding and experimenting with new technologies",
                    "Reading technical documentation",
                    "Learning new tech stacks",
                    "Creating educational content for YouTube"
                ).forEach { hobby ->
                    P { Text("• $hobby") }
                }
            }

            DetailCard("Vision", colorMode) {
                P {
                    Text(
                        "As a Kotlin developer, I aim to push the boundaries of cross-platform development " +
                                "while sharing knowledge and helping others grow in their development journey."
                    )
                }
            }
        }
    }
}


// Helper Components
@Composable
private fun DetailCard(title: String, colorMode: ColorMode, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(8.px)
    ) {
        Column(modifier = Modifier.gap(16.px)) {
            H3(
                attrs = Modifier
                    .fontSize(24.px)
                    .margin(0.px)
                    .toAttrs()
            ) { Text(title) }

            content()
        }
    }
}