package naser09.github.io

import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.CSSFilter
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.green
import org.jetbrains.compose.web.css.Color.whitesmoke

class SitePalette(
    val nearBackground: Color,
    val backgroundColor: CSSColorValue,
    val cobweb: Color,
    val brand: Brand,
) {
    class Brand(
        val primary: Color = Color.rgb(0x8AB4F8),   // Metallic blue
        val accent: Color = Color.rgb(0xD4AF37),    // Metallic gold
        val secondary: Color = Color.rgb(0xC0C0C0),  // Silver
        val tertiary: Color = Color.rgb(0xB87333),   // Copper
    )
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(0xF8F9FD),  // Pearl white
        backgroundColor = whitesmoke,
        cobweb = Color.rgb(0xE8E8E8),          // Platinum light
        brand = SitePalette.Brand(
            primary = Color.rgb(0x4A90E2),      // Steel blue
            accent = Color.rgb(0xFFD700),       // Metallic gold
            secondary = Color.rgb(0xDCDCDC),    // Platinum
            tertiary = Color.rgb(0xCD7F32)      // Bronze
        )
    )

    val dark = SitePalette(
        nearBackground = Color.rgb(0x1A1F2E),   // Deep navy metallic
        backgroundColor = Color.rgb(18, 18, 18),
        cobweb = Color.rgb(0x2C3144),          // Gunmetal
        brand = SitePalette.Brand(
            primary = Color.rgb(0x6FB2FF),      // Electric blue metallic
            accent = Color.rgb(0xFFC857),       // Warm gold
            secondary = Color.rgb(0x9CA3AF),    // Brushed steel
            tertiary = Color.rgb(0xE6BE8A)      // Antique bronze
        )
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    // Light theme
    ctx.theme.palettes.light.apply {
        background = Color.rgb(0xF5F7FA)    // Pearl white background
        color = Color.rgb(0x2D3436)         // Charcoal text
    }

    // Dark theme
    ctx.theme.palettes.dark.apply {
        background = Color.rgb(0x0F1117)    // Deep space metallic
        color = Color.rgb(0xECF0F1)         // Metallic silver text
    }

    // Additional metallic gradients for use in components
    ctx.stylesheet.apply {
        ctx.stylesheet.registerStyleBase("metallic-gradient-light") {
            Modifier.backgroundImage(
                linearGradient(angle = 45.deg) {
                    listOf(
                        Color.rgb(0xF8F9FD),    // Pearl
                        Color.rgb(0xE8E8E8),    // Platinum
                        Color.rgb(0xF8F9FD)     // Pearl
                    ).forEach {
                        add(it)
                    }
                }
            )
        }

        ctx.stylesheet.registerStyleBase("metallic-gradient-dark") {
            Modifier.backgroundImage(
                linearGradient(
                    45.deg
                ) {
                    listOf(
                        Color.rgb(0x1A1F2E),    // Deep navy
                        Color.rgb(0x2C3144),    // Gunmetal
                        Color.rgb(0x1A1F2E)     // Deep navy
                    ).forEach { add(it) }
                }
            )
        }
    }
}

object Theme {
    const val METALIC_GRADIENT_LIGHT = "metallic-gradient-light"
    const val METALIC_GRADIENT_DARK = "metallic-gradient-dark"
}