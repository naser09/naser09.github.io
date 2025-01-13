package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun FooterSection(colorMode: ColorMode) {
    org.jetbrains.compose.web.dom.Footer(
        attrs = Modifier
            .fillMaxWidth()
            .margin(top = 96.px, bottom = 48.px)
            .padding(top = 48.px)
            .borderTop(
                1.px,
                LineStyle.Solid,
                if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(230, 230, 230)
            )
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
                                    hover {
                                        Modifier.color(Color.rgb(100, 100, 100))
                                    }
                                }
                            }
//                            .hover { color(Color.rgb(100, 100, 100)) }
                            .transition(Transition.of("color", 300.ms))
//                            .transition(CSSTransition("color", 300.ms))
                            .toAttrs()
                    ) { Text(link) }
                }
            }
            Div {
                H5 {
                    Span {
                        Text("This Website built with kotlin + Kobweb Framework !")
                    }
                }
            }
        }
    }
}
