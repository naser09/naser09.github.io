package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import naser09.github.io.components.auto_typing_terminal.AutoTypingTerminal
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

data class TechStack(
    val icon:String,
    val width:CSSLengthOrPercentageNumericValue,
    val height:CSSLengthOrPercentageNumericValue,
    val stack:String,
    val link:String=""
)
@Composable
fun TechStackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .onClick { onClick() }
    ) {
        content()
    }
}

@Composable
fun TechStackSection() {
    val colorMode by ColorMode.currentState
    val stackList = listOf(
        TechStack(
            icon = "/icons/kotlin.svg",
            width = 48.px,
            height = 48.px,
            stack = "Kotlin"
        ),
        TechStack(
            icon = "/icons/compose.webp",
            width = 48.px,
            height = 48.px,
            stack = "Jetpack / Jetbrain's Compose"
        ),
        TechStack(
            icon = "/icons/multiplatform.webp",
            width = 96.px,
            height = 48.px,
            stack = "Kotlin Multiplatform"
        ),
        TechStack(
            icon = "/icons/ktor.webp",
            width = 48.px,
            height = 48.px,
            stack = "Ktor Server/Client"
        ),
        TechStack(
            icon = "/icons/gradle.svg",
            width = 48.px,
            height = 48.px,
            stack = "Gradle"
        ),
        TechStack(
            icon = "/icons/sql.svg",
            width = 48.px,
            height = 48.px,
            stack = "SQL Databases"
        ),
        TechStack(
            icon = "/icons/db.svg",
            width = 48.px,
            height = 48.px,
            stack = "NoSQL databases"
        ),
        TechStack(
            icon = "/icons/graphql.svg",
            width = 48.px,
            height = 48.px,
            stack = "KgraphQL/Apollo GraphQl"
        ),
        TechStack(
            icon = "/icons/redis.svg",
            width = 48.px,
            height = 48.px,
            stack = "Redis Cache"
        ),
        TechStack(
            icon = "/icons/more.svg",
            width = 48.px,
            height = 48.px,
            stack = "And many more..."
        ),
    )
    val techStackTitles = listOf(
        "Tools and Technologies I Love",
        "The Stack Behind My Creations",
        "Tech That Powers My Work",
        "My Recent Development Arsenal",
        "Building with These Technologies",
        "My Tech Stack Toolbox",
        "Technologies Powering My Projects"
    )

    Column(
        modifier = Modifier
            .margin(top = 10.vh)
    ) {
        AutoTypingTerminal()

        // Headlines
        Div(
            Modifier
                .fillMaxWidth()
                .alignContent(AlignContent.Center)
                .toAttrs()
        ) {
            H2(
                Modifier.textAlign(TextAlign.Center).toAttrs()
            ) {
                Text(techStackTitles.random())
            }
        }

        // Tech stacks container
        Div(
            Modifier
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
                .justifyContent(JustifyContent.Center)
                .gap(24.px)
                .padding(24.px)
                .backgroundColor(BackgroundColor.Transparent)
                .toAttrs()
        ) {
            stackList.forEach { item ->
                TechStackButton(
                    modifier = Modifier
                        .minWidth(item.width)
                        .background(color = colorMode.toSitePalette().nearBackground)
                        .padding(leftRight = 12.px, topBottom = 6.px)
                        .borderRadius(10.percent)
                        .boxShadow(BoxShadow.of(offsetX = 2.px, offsetY = 1.5.px,
                            color = colorMode.toSitePalette().nearBackground.darkened(0.3f)))
                        .then(TimelineCardStyle.toModifier())
//                        .flexGrow(1)
//                        .flexShrink(1)
//                        .flexBasis(150.px)
                ) {
                    Div(
                        Modifier
                            .display(DisplayStyle.Flex)
                            .flexDirection(FlexDirection.Column)
                            .alignItems(AlignItems.Center)
                            .gap(12.px)
                            .toAttrs()
                    ) {
                        // Image container
                        Div(
                            Modifier
                                .display(DisplayStyle.Flex)
                                .alignItems(AlignItems.Center)
                                .justifyContent(JustifyContent.Center)
                                .toAttrs()
                        ) {
                            Image(
                                src = item.icon,
                                modifier = Modifier
                                    .minWidth(item.width)
                                    .minHeight(item.height)
                                    .maxWidth(item.width*1.2)
                                    .maxHeight(item.height*1.2)
//                                    .mixBlendMode(MixBlendMode.Multiply)
                                    .objectFit(ObjectFit.Contain)
                            )
                        }
                        // Stack name
                        Span(
                            Modifier
                                .textAlign(TextAlign.Center)
                                .color(if (colorMode.isLight) black else white)
                                .fontSize(14.px)
                                .fontWeight(FontWeight.Medium)
                                .toAttrs()
                        ) {
                            Text(item.stack)
                        }
                    }
                }
            }
        }
    }
}