package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
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
import com.varabyte.kobweb.silk.components.icons.fa.FaFacebook
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaLinkedin
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import naser09.github.io.components.SocialButtonStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
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