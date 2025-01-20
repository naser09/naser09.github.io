package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.BackgroundColor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
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
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import naser09.github.io.components.SocialButtonStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.*


@Composable fun HeroSection(colorMode: ColorMode) {
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
                .overflow(Overflow.Hidden)
        ) {
            Image(
                src = "icons/profile.webp",
                modifier = Modifier
                    .backgroundColor(BackgroundColor.Transparent)
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
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Abu Naser") }

            H3(
                attrs = Modifier
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(60, 60, 60))
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Kotlin Developer | Tech Enthusiast") }
            H4(
                Modifier
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(60, 60, 60))
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ){
                Text("Self-Taught Kotlin Multiplatform Developer Crafting Apps for Android, iOS, Web, and Beyond")
            }
            SocialMediaButton()
        }
    }
}


@Composable
private fun SocialMediaButton() {
    val socialLinks = listOf<Pair<String , @Composable ()->Unit>>(
        "https://github.com/naser09" to {
            Image(src = "https://www.svgrepo.com/show/439171/github.svg",
                alt = "github logo from svgrepo.com",
                modifier = Modifier.size(5.vw).minSize(36.px).maxSize(65.px)
                )
        },
        "https://www.linkedin.com/in/abu-naser-bd/" to {
            Image(src = "https://www.svgrepo.com/show/382726/linkedin-linked-in.svg",
                alt = "github logo from svgrepo.com",
                modifier = Modifier.size(5.vw).minSize(36.px).maxSize(65.px)
            ) },
        "https://facebook.com/naser100" to {
            Image(src = "https://www.svgrepo.com/show/349359/facebook.svg",
                alt = "github logo from svgrepo.com",
                modifier = Modifier.size(5.vw).minSize(36.px).maxSize(65.px)
            ) }
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