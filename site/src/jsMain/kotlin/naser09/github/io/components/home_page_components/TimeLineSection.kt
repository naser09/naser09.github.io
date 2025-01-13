package naser09.github.io.components.home_page_components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

data class TimelineItem(
    val year: String,
    val title: String,
    val description: String,
    val technologies: List<String>,
    val achievements: List<String>
)

val TimelineCardStyle = CssStyle{
    base {
        Modifier
            .backgroundColor(if (colorMode == ColorMode.LIGHT)
                Color("rgba(248, 249, 253, 0.15)")
            else
                Color("rgba(26, 31, 46, 0.15)")
            )
            .backdropFilter(blur(10.px))
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(255, 255, 255, 0.2)")
                else
                    Color("rgba(255, 255, 255, 0.1)")
            )
            .borderRadius(20.px)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 8.px,
                blurRadius = 32.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.1)")
                else
                    Color("rgba(0, 0, 0, 0.3)")
            )
            .transition(
//                Transition.of(property = "all", duration = 300.ms, timing = TransitionTimingFunction.EaseInOut)
                transition = Transition.Companion.of("all",300.ms,TransitionTimingFunction.EaseInOut)
            )
    }
    hover {
        Modifier
            .transform { translateY((-8).px) }
            .boxShadow(
                offsetX = 0.px,
                offsetY = 16.px,
                blurRadius = 40.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.15)")
                else
                    Color("rgba(0, 0, 0, 0.4)")
            )
    }
}

val YearStyle = CssStyle{
    base {
        Modifier
            .fontSize(64.px)
            .fontWeight(FontWeight.Bold)
            .color(if (colorMode == ColorMode.LIGHT)
                Color("rgba(0, 0, 0, 0.8)")
            else
                Color("rgba(255, 255, 255, 0.8)")
            )
            .textShadow(
                offsetX = 2.px,
                offsetY = 2.px,
                blurRadius = 4.px,
                color = if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.1)")
                else
                    Color("rgba(0, 0, 0, 0.3)")
            )
    }
}

@Composable
fun TimelineSection(colorMode: ColorMode) {
    val timelineItems = listOf(
        TimelineItem(
            year = "2017",
            title = "Android Development Journey Begins",
            description = "Started my journey in Android development with Java, learning core concepts and building basic applications. Focused on understanding Android lifecycle, UI development with XML layouts, and fundamental programming concepts.",
            technologies = listOf(
                "Java SE 8",
                "Android SDK",
                "XML Layouts",
                "SQLite",
                "Android Studio"
            ),
            achievements = listOf(
                "Built first Android app from scratch",
                "Learned OOP principles",
                "Mastered Android Activity lifecycle"
            )
        ),
        TimelineItem(
            year = "2019",
            title = "Kotlin Migration & Modern Android",
            description = "Transitioned to Kotlin for Android development, embracing modern Android development practices. Implemented MVVM architecture, Coroutines for async programming, and adopted Android Jetpack components.",
            technologies = listOf(
                "Kotlin 1.3",
                "Android Architecture Components",
                "Room Database",
                "ViewModel & LiveData",
                "Coroutines",
                "Retrofit",
                "Dagger 2"
            ),
            achievements = listOf(
                "Migrated existing Java projects to Kotlin",
                "Implemented MVVM architecture",
                "Published first app on Play Store"
            )
        ),
        TimelineItem(
            year = "2020",
            title = "Jetpack Compose Revolution",
            description = "Embraced declarative UI with Jetpack Compose, revolutionizing Android UI development. Focused on building modern, reactive applications with improved user experiences and maintainable codebases.",
            technologies = listOf(
                "Jetpack Compose",
                "Compose Navigation",
                "Hilt Dependency Injection",
                "Flow",
                "Material Design Components",
                "Firebase Suite",
                "Google Play Console"
            ),
            achievements = listOf(
                "Published two successful apps on Play Store",
                "Implemented complex custom composables",
                "Integrated Firebase services"
            )
        ),
        TimelineItem(
            year = "2021",
            title = "Multiplatform & Content Creation",
            description = "Expanded horizons with Kotlin Multiplatform development and started sharing knowledge through YouTube content creation. Focused on building shared codebases for multiple platforms.",
            technologies = listOf(
                "Kotlin Multiplatform Mobile",
                "Swift/SwiftUI integration",
                "Ktor Client",
                "SQLDelight",
                "YouTube Content Creation",
                "Video Editing Tools"
            ),
            achievements = listOf(
                "Launched YouTube channel",
                "Built first KMM application",
                "Growing developer community"
            )
        ),
        TimelineItem(
            year = "2022-Present",
            title = "Full-Stack Development",
            description = "Ventured into backend development with Ktor and modern web technologies. Built comprehensive full-stack applications with focus on scalability and performance.",
            technologies = listOf(
                "Ktor Server",
                "Exposed ORM",
                "Redis Cache",
                "PostgreSQL",
                "Kobweb",
                "Docker",
                "Kubernetes",
                "AWS Services"
            ),
            achievements = listOf(
                "Developed scalable backend systems",
                "Created admin dashboard with Kobweb",
                "Implemented CI/CD pipelines"
            )
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .overflow(Overflow.Auto)
            .scrollBehavior(ScrollBehavior.Smooth)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // Remove fixed height from container to allow natural content flow
                .minHeight(100.vh)
        ) {
            timelineItems.forEachIndexed { index, item ->
                TimelineItemView(
                    item = item,
                    isLeft = index % 2 == 0,
                    colorMode = colorMode
                )
            }
        }
    }
}

@Composable
private fun TimelineItemView(
    item: TimelineItem,
    isLeft: Boolean,
    colorMode: ColorMode
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.vh)
            .scrollSnapAlign(ScrollSnapAlign.Center)
            .position(Position.Relative)
    ) {
        // Timeline line
        Box(
            modifier = Modifier
                .position(Position.Absolute)
                .left(50.percent)
                .width(2.px)
                .height(100.percent)
                .backgroundColor(if (colorMode == ColorMode.LIGHT)
                    Color("rgba(0, 0, 0, 0.2)")
                else
                    Color("rgba(255, 255, 255, 0.2)")
                )
        )

        // Year text
        Span(
            attrs = Modifier
                .position(Position.Absolute)
                .top(50.percent)
                .left(if (isLeft) 75.percent else 25.percent)
                .transform { translateY((-50).percent) }
                .then(YearStyle.toModifier())
                .toAttrs()
        ) {
            Text(item.year)
        }

        // Content card
        Box(
            modifier = Modifier
                .position(Position.Absolute)
                .width(45.percent)
                .left(if (isLeft) 2.percent else 53.percent)
                .top(50.percent)
                .transform { translateY((-50).percent) }
                .padding(20.px)
                .then(TimelineCardStyle.toModifier())
        ) {
            Column(modifier = Modifier.gap(16.px)) {
                H2(attrs = Modifier
                    .margin(0.px)
                    .fontSize(24.px)
                    .fontWeight(FontWeight.Bold)
                    .color(if (colorMode == ColorMode.LIGHT)
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(51, 51, 51)
                    else
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(245, 245, 245)
                    )
                    .toAttrs()
                ) {
                    Text(item.title)
                }

                P(attrs = Modifier
                    .margin(0.px)
                    .lineHeight(1.6)
                    .color(if (colorMode == ColorMode.LIGHT)
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(71, 71, 71)
                    else
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(200, 200, 200)
                    )
                    .toAttrs()
                ) {
                    Text(item.description)
                }

                H4(attrs = Modifier
                    .margin(topBottom = 8.px)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.SemiBold)
                    .color(if (colorMode == ColorMode.LIGHT)
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(51, 51, 51)
                    else
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(245, 245, 245)
                    )
                    .toAttrs()
                ) {
                    Text("Technologies")
                }

                Div(attrs = Modifier
                    .display(DisplayStyle.Flex)
                    .flexWrap(FlexWrap.Wrap)
                    .gap(8.px)
                    .toAttrs()
                ) {
                    item.technologies.forEach { tech ->
                        Span(
                            attrs = Modifier
                                .padding(leftRight = 12.px, topBottom = 4.px)
                                .backgroundColor(if (colorMode == ColorMode.LIGHT)
                                    Color("rgba(0, 0, 0, 0.05)")
                                else
                                    Color("rgba(255, 255, 255, 0.1)")
                                )
                                .borderRadius(12.px)
                                .fontSize(14.px)
                                .color(if (colorMode == ColorMode.LIGHT)
                                    com.varabyte.kobweb.compose.ui.graphics.Color.rgb(71, 71, 71)
                                else
                                    com.varabyte.kobweb.compose.ui.graphics.Color.rgb(200, 200, 200)
                                )
                                .toAttrs()
                        ) {
                            Text(tech)
                        }
                    }
                }

                H4(attrs = Modifier
                    .margin(topBottom = 8.px)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.SemiBold)
                    .color(if (colorMode == ColorMode.LIGHT)
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(51, 51, 51)
                    else
                        com.varabyte.kobweb.compose.ui.graphics.Color.rgb(245, 245, 245)
                    )
                    .toAttrs()
                ) {
                    Text("Key Achievements")
                }

                Ul(attrs = Modifier
                    .margin(0.px)
                    .padding(left = 20.px)
                    .toAttrs()
                ) {
                    item.achievements.forEach { achievement ->
                        Li(attrs = Modifier
                            .color(if (colorMode == ColorMode.LIGHT)
                                com.varabyte.kobweb.compose.ui.graphics.Color.rgb(71, 71, 71)
                            else
                                com.varabyte.kobweb.compose.ui.graphics.Color.rgb(200, 200, 200)
                            )
                            .toAttrs()
                        ) {
                            Text(achievement)
                        }
                    }
                }
            }
        }
    }
}