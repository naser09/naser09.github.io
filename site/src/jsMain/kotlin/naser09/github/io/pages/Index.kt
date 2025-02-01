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
