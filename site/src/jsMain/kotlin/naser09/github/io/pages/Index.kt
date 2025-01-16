package naser09.github.io.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
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
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.auto_typing_terminal.AutoTypingTerminal
import naser09.github.io.components.home_page_components.FooterSection
import naser09.github.io.components.home_page_components.HeroSection
import naser09.github.io.components.home_page_components.TechStackSection
import naser09.github.io.components.home_page_components.TimelineSection
import naser09.github.io.components.neumorphism.GlassMorphismButton
import naser09.github.io.components.neumorphism.GlassMorphismCardStyle
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*

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
                .backgroundColor(colorMode.toSitePalette().backgroundColor)
                .color(if (colorMode == ColorMode.DARK) white else black)
                .onMouseMove { scrolled = true }
        ) {
            Column(
                modifier = Modifier.width(100.vw)
                    .margin { auto }
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
                    HeroSection(colorMode)
                    // Tech Stack
                    TechStackSection()
                }

                // Timeline
                TimelineSection(colorMode)
                // Footer
                FooterSection(colorMode)
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
