package naser09.github.io.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.home_page_components.FooterSection
import naser09.github.io.components.home_page_components.HeroSection
import naser09.github.io.components.home_page_components.TechStackSection
import naser09.github.io.components.home_page_components.TimelineSection
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

@Page
@Composable
fun HomePage() {
    val  colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    val svg = """
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.--><path d="M549.7 124.1c-6.3-23.7-24.8-42.3-48.3-48.6C458.8 64 288 64 288 64S117.2 64 74.6 75.5c-23.5 6.3-42 24.9-48.3 48.6-11.4 42.9-11.4 132.3-11.4 132.3s0 89.4 11.4 132.3c6.3 23.7 24.8 41.5 48.3 47.8C117.2 448 288 448 288 448s170.8 0 213.4-11.5c23.5-6.3 42-24.2 48.3-47.8 11.4-42.9 11.4-132.3 11.4-132.3s0-89.4-11.4-132.3zm-317.5 213.5V175.2l142.7 81.2-142.7 81.2z"/></svg>
    """.trimIndent()
    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(colorMode.toSitePalette().backgroundColor)
                .color(if (colorMode == ColorMode.DARK) white else black)
                .margin(bottom = 13.vh)
        ) {
            Column(
                modifier = Modifier
                    .width(100.vw)
                    .gap(10.px)
                    .padding(leftRight = 4.vw),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Svg {

                }
                if (breakpoint >= Breakpoint.MD){
                    Row(Modifier.width(100.vw),
                        verticalAlignment = Alignment.Top,
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
                    Box(Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center){
                        HeroSection(colorMode)
                    }
                    // Tech Stack
                    TechStackSection()
                }

                // Timeline
                TimelineSection(colorMode)
                // Footer
                FooterSection(colorMode)
            }

            // Scroll Indicator
            //ScrollIndicator(colorMode)
        }
    }
}
