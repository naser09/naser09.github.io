package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Composable
private fun ScrollIndicator(colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .position(Position.Fixed)
            .bottom(24.px)
            .left(50.percent)
            .transform { translateX((-50).percent) }
            .padding(8.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(245, 245, 245))
            .borderRadius(24.px)
            .opacity(70)
    ) {
        Box(
            modifier = Modifier
                .size(24.px)
                .border(1.px, LineStyle.Solid, if (colorMode == ColorMode.DARK) white else black)
                .borderRadius(50.percent)
                .animation(
//s
                )
                .transform { translateY(8.px) }
        )
    }
}

