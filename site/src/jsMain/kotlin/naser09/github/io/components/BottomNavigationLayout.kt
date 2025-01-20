package naser09.github.io.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.*
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
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import naser09.github.io.Theme
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

sealed class NavigationRoute(val title: String,val route: String,val shortTitle:String = title, ) {
    data object Home : NavigationRoute("Home", "/")
    data object Projects : NavigationRoute("Projects", "projects")
    data object Youtube : NavigationRoute("Youtube", "video")
    data object AboutMe : NavigationRoute("About Me", "about", shortTitle = "About")
    data object Academic : NavigationRoute("Academic", "academic")
    data object TechStack : NavigationRoute("Tech Stack", "tech_stack", shortTitle = "Stack")

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
            .margin(leftRight = 2.5.vw)
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
    val breakpoint = rememberBreakpoint()
    Box(modifier = Modifier
        .fillMaxSize(100.percent)) {
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
                modifier = BottomNavBackDropStyle.
                toModifier(),
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
                        P (
                            if ((page is NavigationRoute.Home && ctx.route.slug.isEmpty()) || ctx.route.slug == page.route){
                                Modifier
                                    .fontWeight(FontWeight.ExtraBold)
                                    .scale(1.2)
                                    .toAttrs()
                            }else Modifier.fontWeight(FontWeight.Normal).toAttrs()
                        ){
                            if (breakpoint>=Breakpoint.MD){
                                Text(page.title)
                            }else{
                                Text(page.shortTitle)
                            }
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
        .margin(left = 96.percent, top = 2.percent, right = 2.percent)
        .zIndex(1)
        .position(Position.Fixed),
        contentAlignment = Alignment.TopEnd
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
                Image(src = "https://www.svgrepo.com/show/484700/sun.svg",
                    modifier = Modifier.size(3.vw)
                        .minSize(24.px)
                        .maxSize(48.px),
                    alt = "turn on light mode")
            } else {
                Image(src = "https://www.svgrepo.com/show/512505/moon-1248.svg",
                    modifier = Modifier.size(3.vw).minSize(24.px).maxSize(48.px),
                    alt = "turn on dark mode")
            }
        }
    }
}