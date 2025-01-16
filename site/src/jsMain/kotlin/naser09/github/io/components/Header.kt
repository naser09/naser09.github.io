package naser09.github.io.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.background
import com.varabyte.kobweb.compose.css.color
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.navigation.open
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Composable
fun PageHeader(
    title: String,
    subtitle: String,
    colorMode: ColorMode,
    icon: String,
    links:String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(40.vh)
            .backgroundColor(colorMode.toSitePalette().backgroundColor)
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
                    if (links.isNotBlank() || links.isNotBlank()){
                        window.open(links, OpenLinkStrategy.IN_NEW_TAB)
                    }
                }
                .toAttrs()) {
                Image(src = icon, modifier = Modifier
                    .maxHeight(48.px)
                    .margin(right = 6.px))
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
