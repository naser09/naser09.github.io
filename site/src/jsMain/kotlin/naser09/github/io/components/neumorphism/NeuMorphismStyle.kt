package naser09.github.io.components.neumorphism

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import naser09.github.io.SitePalettes
import org.jetbrains.compose.web.css.*

val NeuMorphismButtonStyle = CssStyle {
    base {
        val colors = if (colorMode == ColorMode.LIGHT) SitePalettes.light else SitePalettes.dark
        Modifier
            .border(width = 0.px)
            .borderRadius(10.px)
            .backgroundColor(colors.nearBackground)
            .boxShadow(
                offsetX = 8.px,
                offsetY = 8.px,
                blurRadius = 15.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.2)")
                else
                    Color("rgba(0, 0, 0, 0.4)")
            )
            .boxShadow(
                offsetX = (-8).px,
                offsetY = (-8).px,
                blurRadius = 15.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.7)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
            .transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
            .cursor(Cursor.Pointer)
    }
    hover {
        Modifier
            .boxShadow(
                offsetX = 4.px,
                offsetY = 4.px,
                blurRadius = 8.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.2)")
                else
                    Color("rgba(0, 0, 0, 0.4)")
            )
            .boxShadow(
                offsetX = (-4).px,
                offsetY = (-4).px,
                blurRadius = 8.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.7)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
            .transform { scale(0.98) }
    }
}

val NeuMorphismCardStyle = CssStyle {
    base {
        val colors = if (colorMode == ColorMode.LIGHT) SitePalettes.light else SitePalettes.dark
        Modifier
            .border(width = 0.px)
            .borderRadius(15.px)
            .backgroundColor(colors.nearBackground)
            .boxShadow(
                offsetX = 12.px,
                offsetY = 12.px,
                blurRadius = 20.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.2)")
                else
                    Color("rgba(0, 0, 0, 0.4)")
            )
            .boxShadow(
                offsetX = (-12).px,
                offsetY = (-12).px,
                blurRadius = 20.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.7)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
            .transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
    }
    hover {
        Modifier
            .boxShadow(
                offsetX = 8.px,
                offsetY = 8.px,
                blurRadius = 15.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.2)")
                else
                    Color("rgba(0, 0, 0, 0.4)")
            )
            .boxShadow(
                offsetX = (-8).px,
                offsetY = (-8).px,
                blurRadius = 15.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.7)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
    }
}

val GlassMorphismCardStyle = CssStyle {
    base {
        val colors = if (colorMode == ColorMode.LIGHT) SitePalettes.light else SitePalettes.dark

        Modifier
            .backgroundColor(if (colorMode == ColorMode.LIGHT)
                Color("rgba(248, 249, 253, 0.15)") // Light theme background
            else
                Color("rgba(26, 31, 46, 0.15)") // Dark theme background
            )
            .backdropFilter(filters = arrayOf(blur(10.px)))
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.2)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
            .borderRadius(15.px)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 8.px,
                blurRadius = 32.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.1)")
                else
                    Color("rgba(0, 0, 0, 0.3)")
            )
            .transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
    }
    hover {
        Modifier
            .backgroundColor(if (colorMode == ColorMode.LIGHT)
                Color("rgba(248, 249, 253, 0.2)") // Light theme background
            else
                Color("rgba(26, 31, 46, 0.2)") // Dark theme background
            )
            .transform { translateY((-5).px) }
            .boxShadow(
                offsetX = 0.px,
                offsetY = 12.px,
                blurRadius = 36.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.12)")
                else
                    Color("rgba(0, 0, 0, 0.35)")
            )
    }
}

