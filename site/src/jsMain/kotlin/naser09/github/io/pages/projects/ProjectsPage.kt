package naser09.github.io.pages.projects

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import kotlinx.coroutines.delay
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black
import kotlin.time.Duration.Companion.seconds

private data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val type: ProjectType,
    val releaseLink: String? = null,
    val githubLink: String? = null,
    val images: List<String> // Updated to support multiple images
)

private enum class ProjectType {
    PRODUCTION, OPEN_SOURCE, PET_PROJECT, FREELANCE
}

val ProjectCardStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(colorMode.toSitePalette().nearBackground)
//            .transition(Transition.group {
//                transform(300.ms)
//                boxShadow(300.ms)
//            })
    }
    hover {
        Modifier
            .transform { scale(1.02f) }
//            .boxShadow(0.px, 4.px, 12.px, Color.rgba(0, 0, 0, 0.1f))
    }

    Breakpoint.MD {
        Modifier.padding(24.px)
    }
}

@Page("/projects")
@Composable
fun ProjectsPage() {
    val colorMode by ColorMode.currentState

    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else white)
                .color(if (colorMode == ColorMode.DARK) white else black)
                .margin(bottom = 13.vh)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                PageHeader(
                    "Projects",
                    "Senior Kotlin Developer specialized in Multiplatform Development and Backend Services",
                    colorMode,
                    "icons/github.svg"
                )

                ProjectsGrid(colorMode)
            }
        }
    }
}

@Composable
private fun ProjectsGrid(colorMode: ColorMode) {
    val projects = listOf(
        Project(
            "KMPCommerce",
            "A full-featured e-commerce application built with Kotlin Multiplatform. Supports iOS and Android with shared business logic, networking, and local storage.",
            listOf("Kotlin Multiplatform", "Ktor", "SQLDelight", "Compose Multiplatform"),
            ProjectType.PRODUCTION,
            releaseLink = "https://play.google.com/store/apps/details?id=com.example.kmpcommerce",
            githubLink = "https://github.com/example/kmpcommerce",
            images = listOf(
                "projects/kmpcommerce1.png",
                "projects/kmpcommerce2.png",
                "projects/kmpcommerce3.png"
            )
        ),
        // Add more projects with multiple images
        Project(
            "Ktor Backend Template",
            "Production-ready Ktor backend template with authentication, authorization, and database integration.",
            listOf("Ktor", "PostgreSQL", "Redis", "Docker"),
            ProjectType.OPEN_SOURCE,
            githubLink = "https://github.com/example/ktor-template",
            images = listOf(
                "projects/ktor1.png",
                "projects/ktor2.png"
            )
        )
        // Add more projects here
        ,
        Project(
            "KMPCommerce",
            "A full-featured e-commerce application built with Kotlin Multiplatform. Supports iOS and Android with shared business logic, networking, and local storage. Uses Ktor for the backend API.",
            listOf("Kotlin Multiplatform", "Ktor", "SQLDelight", "Compose Multiplatform", "Swift UI"),
            ProjectType.PRODUCTION,
            releaseLink = "https://play.google.com/store/apps/details?id=com.example.kmpcommerce",
            githubLink = "https://github.com/example/kmpcommerce",
            images = listOf ( "projects/kmpcommerce.png")
        ),
        Project(
            "Ktor Backend Template",
            "Production-ready Ktor backend template with authentication, authorization, database integration, and deployment configurations. Features comprehensive testing and documentation.",
            listOf("Ktor", "PostgreSQL", "Redis", "Docker", "GitHub Actions"),
            ProjectType.OPEN_SOURCE,
            githubLink = "https://github.com/example/ktor-template",
            images = listOf ( "projects/ktor-template.png")
        ),
        Project(
            "KotlinConf App",
            "Official conference companion app built with Compose Multiplatform. Features schedule management, speaker profiles, and real-time updates.",
            listOf("Kotlin", "Compose Multiplatform", "Ktor", "Firebase"),
            ProjectType.PRODUCTION,
            releaseLink = "https://play.google.com/store/apps/details?id=org.kotlinconf.app",
            githubLink = "https://github.com/example/kotlinconf-app",
            images = listOf ( "projects/kotlinconf.png")
        ),
        Project(
            "KMP Analytics SDK",
            "Cross-platform analytics SDK built with Kotlin Multiplatform. Provides seamless integration for iOS and Android apps with a single codebase.",
            listOf("Kotlin Multiplatform", "Coroutines", "SQLDelight", "Kotlin Serialization"),
            ProjectType.OPEN_SOURCE,
            githubLink = "https://github.com/example/kmp-analytics",
            images = listOf ( "projects/analytics-sdk.png")
        ),
        Project(
            "Compose Design System",
            "Comprehensive design system built with Compose Multiplatform, featuring 50+ reusable components, theming support, and documentation.",
            listOf("Compose Multiplatform", "Kotlin", "Material Design"),
            ProjectType.FREELANCE,
            githubLink = "https://github.com/example/compose-design-system",
            images = listOf("projects/design-system.png")
        ),
        Project(
            "KMP Weather App",
            "Weather application showcasing Kotlin Multiplatform capabilities with shared networking, caching, and business logic.",
            listOf("Kotlin Multiplatform", "Ktor", "Kotlin Flow", "Compose Multiplatform"),
            ProjectType.PET_PROJECT,
            releaseLink = "https://play.google.com/store/apps/details?id=com.example.kmpweather",
            githubLink = "https://github.com/example/kmp-weather",
            images = listOf("projects/weather-app.png")
        )
    )

    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.px)
    ) {
        SimpleGrid(
            modifier = Modifier
                .fillMaxWidth()
                .gap(32.px),
            numColumns = numColumns(base = 1, sm = 1, md = 2, lg = 3)
        ) {
            projects.forEach { project ->
                ProjectCard(project, colorMode)
            }
        }
    }
}

@Composable
private fun ImageSlider(images: List<String>, modifier: Modifier = Modifier) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var key by remember { mutableStateOf(0) }

    // Effect to handle auto-sliding
    LaunchedEffect(Unit) {
        while (true) {
            delay(3.seconds)
            currentImageIndex = (currentImageIndex + 1) % images.size
            key++ // Force recomposition
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.px)
            .position(Position.Relative)
    ) {
        // Current image
        Image(
            src = images[currentImageIndex],
            modifier = Modifier
                .fillMaxWidth()
                .height(200.px)
                .objectFit(ObjectFit.Cover)
                .borderRadius(8.px)
                .styleModifier {
                    property("transition", "opacity 0.5s ease-in-out")
                }
        )

        // Navigation dots
        Row(
            modifier = Modifier
                .position(Position.Absolute)
                .bottom(16.px)
                .left(50.percent)
                .transform { translateX(-50.percent) }
                .gap(8.px)
                .zIndex(1),
            horizontalArrangement = Arrangement.Center
        ) {
            images.forEachIndexed { index, _ ->
                Box(
                    Modifier
                        .size(8.px)
                        .borderRadius(50.percent)
                        .backgroundColor(
                            if (index == currentImageIndex) Color.rgb(255, 255, 255)
                            else Color.rgba(255, 255, 255, 0.5f)
                        )
                        .cursor(Cursor.Pointer)
                        .onClick { currentImageIndex = index }
                )
            }
        }
    }
}
@Composable
private fun ProjectCard(project: Project, colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(12.px)
            .styleModifier {
                property("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)")
                property("transition", "transform 0.3s ease, box-shadow 0.3s ease")
            }
//            .hover(
//                Modifier.styleModifier {
//                    property("transform", "translateY(-5px)")
//                    property("box-shadow", "0 10px 20px rgba(0, 0, 0, 0.2)")
//                }
//            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.px)
                .gap(20.px)
        ) {
            // Image Slider
            if (project.images.isNotEmpty()) {
                ImageSlider(project.images)
            }

            // Project Type Badge
            Span(
                attrs = Modifier
                    .padding(4.px, 8.px)
                    .backgroundColor(getProjectTypeColor(project.type, colorMode))
                    .borderRadius(4.px)
                    .fontSize(14.px)
                    .toAttrs()
            ) { Text(project.type.name.replace("_", " ")) }

            // Title
            H3(
                attrs = Modifier
                    .fontSize(24.px)
                    .margin(0.px)
                    .toAttrs()
            ) { Text(project.title) }

            // Description
            P(
                attrs = Modifier
                    .margin(0.px)
                    .lineHeight(1.6)
                    .toAttrs()
            ) { Text(project.description) }

            // Technology tags
            Row(
                modifier = Modifier
                    .gap(8.px)
                    .flexWrap(FlexWrap.Wrap)
            ) {
                project.technologies.forEach { tech ->
                    Span(
                        attrs = Modifier
                            .padding(4.px, 8.px)
                            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(230, 230, 230))
                            .borderRadius(4.px)
                            .fontSize(14.px)
                            .toAttrs()
                    ) { Text(tech) }
                }
            }

            // Links
            Row(
                modifier = Modifier
                    .gap(16.px)
//                    .marginTop(8.px)
            ) {
                project.releaseLink?.let {
                    A(
                        href = it,
                        attrs = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .color(if (colorMode == ColorMode.DARK) white else black)
                            .toAttrs()
                    ) { Text("Download →") }
                }

                project.githubLink?.let {
                    A(
                        href = it,
                        attrs = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .color(if (colorMode == ColorMode.DARK) white else black)
                            .toAttrs()
                    ) { Text("GitHub →") }
                }
            }
        }
    }
}

private fun getProjectTypeColor(type: ProjectType, colorMode: ColorMode): Color {
    return when (type) {
        ProjectType.PRODUCTION -> if (colorMode == ColorMode.DARK) Color.rgb(0, 100, 0) else Color.rgb(200, 255, 200)
        ProjectType.OPEN_SOURCE -> if (colorMode == ColorMode.DARK) Color.rgb(100, 0, 100) else Color.rgb(255, 200, 255)
        ProjectType.PET_PROJECT -> if (colorMode == ColorMode.DARK) Color.rgb(0, 0, 100) else Color.rgb(200, 200, 255)
        ProjectType.FREELANCE -> if (colorMode == ColorMode.DARK) Color.rgb(100, 100, 0) else Color.rgb(255, 255, 200)
    }
}