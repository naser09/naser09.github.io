package naser09.github.io.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.navigation.open
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Composable
fun PageHeader(
    title: String,
    subtitle: String,
    colorMode: ColorMode,
    icon: String = "icons/youtube.svg",
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(40.vh)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(25, 25, 25) else Color.rgb(245, 245, 245))
            .padding(24.px),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.maxWidth(800.px).gap(16.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Div(Modifier
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .cursor(Cursor.Pointer)
                .onClick {
                    window.open("https://www.youtube.com/@nCodeLab", OpenLinkStrategy.IN_NEW_TAB)
                }
                .toAttrs()) {
                Image(src = icon, modifier = Modifier.maxHeight(48.px).margin(right = 6.px))
                H1(
                    attrs = Modifier
                        .fontSize(48.px)
                        .fontWeight(700)
                        .margin(0.px)
                        .textAlign(TextAlign.Center)
                        .cursor(Cursor.Pointer)
                        .toAttrs()
                ) {
                    Text(title)
                }
            }

            P(
                attrs = Modifier
                    .fontSize(20.px)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(60, 60, 60))
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text(subtitle) }
        }
    }
}
