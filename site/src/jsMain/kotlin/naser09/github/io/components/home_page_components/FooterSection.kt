package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun FooterSection(colorMode: ColorMode) {
    Footer(
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
            Div {
                H5 {
                    Span {
                        Text("This Website built with kotlin + Kobweb Framework !")
                    }
                }
            }
            Div {
                H5 {
                    Span {
                        Text("Developed by abu naser .")
                    }
                }
            }
        }
    }
}

