package naser09.github.io.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.BackgroundColor
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifySelf
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import naser09.github.io.Theme
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

sealed class NavigationRoute(val title: String, val route: String) {
    data object Home : NavigationRoute("Home", "/")
    data object Projects : NavigationRoute("Projects", "projects")
    data object Youtube : NavigationRoute("Youtube", "video")
    data object AboutMe : NavigationRoute("About Me", "about")
    data object Academic : NavigationRoute("Academic", "academic")
    data object TechStack : NavigationRoute("Tech Stack", "tech_stack")

    companion object {
        val pages = listOf(
            Home,
            Projects,
            Youtube,
            AboutMe,
            Academic,
            TechStack
        )
    }
}
val SocialButtonStyle = CssStyle{
    hover {
        Modifier
            .fontWeight(800)
            .scale(1.3)
            .backgroundColor(rgba(255, 255, 255, 0.1))
    }
    base {
        Modifier
            .padding(8.px)
            .backgroundColor(colorMode.toSitePalette().nearBackground)
            .color(colorMode.toSitePalette().brand.primary)
            .border(0.px)
            .borderRadius(20.percent)
            .cursor(Cursor.Pointer)
            .transition(Transition.of("all", 200.ms))
    }
}

val NavigationButtonStyle = CssStyle{
    (Breakpoint.ZERO until Breakpoint.MD){
        Modifier
            .padding(6.px,8.px)
    }
    Breakpoint.MD{
        Modifier
            .padding(8.px,12.px)
    }
    Breakpoint.LG{
        Modifier
            .padding(12.px,16.px)
    }
    base {
        Modifier
            .backgroundColor(BackgroundColor.Inherit)
            .classNames(if (colorMode.isLight) Theme.METALIC_GRADIENT_LIGHT else Theme.METALIC_GRADIENT_DARK)
            .border(0.px)
            .borderRadius(16.px)
            .cursor(Cursor.Pointer)
            .transition(Transition.of("all", 200.ms))
    }
    hover{
        Modifier
            .backgroundImage(gradient =
            linearGradient(angle = 90.deg){
                add(colorMode.toSitePalette().brand.primary.toRgb().copy(alpha = 70))
                add(colorMode.toSitePalette().brand.secondary)
            })
            .backdropFilter(filters = arrayOf(blur(10.px)))
            .scale(1.5)
    }
}
val BottomNavBackDropStyle = CssStyle {
    (Breakpoint.ZERO until Breakpoint.MD){
        Modifier
            .width(95.vw)
            .padding(leftRight = 10.px, topBottom = 6.px)
            .bottom(12.px)
            .borderRadius(12.px)
    }
    Breakpoint.MD{
        Modifier
            .padding(leftRight = 20.px, topBottom = 16.px)
            .left(50.percent)
            .bottom(24.px)
            .transform { translateX((-50).percent) }
            .borderRadius(24.px)
            .padding(8.px)
    }
    Breakpoint.LG{
     Modifier.padding(30.px,20.px)
         .padding(16.px)
    }
    base {
        Modifier
            .zIndex(1)
            .position(Position.Fixed)
            .backgroundColor(BackgroundColor.Transparent)
            .backgroundImage(gradient =
            linearGradient(angle = 45.deg){
                add(colorMode.toSitePalette().brand.primary.toRgb().copy(alpha = 70))
                add(colorMode.toSitePalette().brand.secondary)
            })
            .color(colorMode.toSitePalette().brand.secondary)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 12.px,
                color = Color.rgb(0, 0, 0).copy(alpha = 1)
            )
            .backdropFilter(filters = arrayOf(blur(20.px)))
//                .backdropFilter("blur(10px)")
            .transition(transition = Transition.of("all", 300.ms, AnimationTimingFunction.EaseInOut))
    }
}

@Composable
fun BottomNavigationLayout(
    content: @Composable () -> Unit
) {
    val ctx = rememberPageContext()
    var colorMode by ColorMode.currentState
    Box(modifier = Modifier.fillMaxSize(100.percent)) {
        // Main content
        Box(Modifier.fillMaxSize().zIndex(0)) {
            content()
        }
        Box(Modifier.width(100.vw)
            .height(100.vw)){
            TopRightControls{
                colorMode = colorMode.opposite
            }
        }

        // Navigation container with backdrop blur
            Row(
                modifier = BottomNavBackDropStyle.toModifier().justifySelf(JustifySelf.Center),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationRoute.pages.forEach { page ->
                    Button(
                        attrs = NavigationButtonStyle.toModifier()
                            .onClick {
                                ctx.router.navigateTo(page.route)
                            }
                            .toAttrs()
                    ) {
                        P {
                            Text(page.title)
                        }
                    }
                }
        }
    }
}

@Composable
fun TopRightControls(onClick:()->Unit) {
    val colorMode = ColorMode.currentState.value
    val isDark = colorMode == ColorMode.DARK
        // Theme Toggle Button
    Box(modifier = Modifier
        .zIndex(1)
        .position(Position.Fixed)
        .top(20.px)
        .right(20.px)
        .gap(16.px)
    ){
        Button(
            attrs = SocialButtonStyle.toModifier()
                .color(if (isDark) Colors.White else Colors.Black)
                .onClick {
                    onClick()
                    //ColorMode.toggleCurrentMode()
                }
                .toAttrs()
        ) {
            if (isDark) {
                FaSun()
            } else {
                FaMoon()
            }
        }
    }
}