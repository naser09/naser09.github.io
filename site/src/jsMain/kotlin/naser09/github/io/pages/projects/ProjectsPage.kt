package naser09.github.io.pages.projects

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import naser09.github.io.toSitePalette
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.dom.*
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
    PRODUCTION, OPEN_SOURCE, PET_PROJECT, FREELANCE , YOUTUBE ,IN_PROGRESS
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
                    "icons/github.svg",
                    ""
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
            title = "Lineage note: Cherry tree notes",
            description = "A note-taking app with a focus on streamlined navigation and modern design.",
            technologies = listOf(
                "androidx navigation", "jetpack compose", "admob", "Material 3",
                "room database", "shared preference", "multi-module",
                "scoped storage", "GSON", "MVVM architecture"
            ),
            type = ProjectType.PRODUCTION,
            releaseLink = "https://play.google.com/store/apps/details?id=com.blogspot.lineagenote",
            githubLink = null,
            images = listOf()
        ),
        Project(
            title = "PDF Tools: Web2PDF, Merge, Split",
            description = "A comprehensive PDF utility app for creating and managing PDF files.",
            technologies = listOf(
                "androidx", "jetpack compose", "Material 3", "shared preference",
                "multi-module", "MVVM architecture", "web view", "PDF Box"
            ),
            type = ProjectType.PRODUCTION,
            releaseLink = "https://play.google.com/store/apps/details?id=com.blogspot.web2pdf",
            githubLink = null,
            images = listOf() // Add image URLs here if available
        ),    Project(
            title = "KMP Note Pad app with YouTube playlist",
            description = "An Kotlin Multiplatform app with a complete development tutorial available on YouTube.",
            technologies = listOf(
                "kotlin", "swift", "xcode", "android studio", "kotlin multiplatform",
                "sqlDelight", "decompose", "RxKotlin", "coroutine", "MviKotlin",
                "swift UI", "multiplatform setting"
            ),
            type = ProjectType.YOUTUBE,
            releaseLink = "https://www.youtube.com/playlist?list=PLXzlJ385171s7A9iEngwx3b2RlCRAbqqy",
            githubLink = null,
            images = listOf()
        ),
        Project(
            "Ktor Backend Chat server for youtube video ,",
            "A ktor backend server created to demonstrate how it work in my youtube video ,",
            listOf("Kotlin", "Ktor", "SQLDelight", "kotlinX serialization","web socket"),
            ProjectType.YOUTUBE,
            releaseLink = "https://www.youtube.com/playlist?list=PLXzlJ385171vyDDz2eliB1CWp-FMFX06x",
            githubLink = "https://github.com/naser09/chat_server_yt.git",
            images = listOf(
                "projects/kmpcommerce1.png",
                "projects/kmpcommerce2.png",
                "projects/kmpcommerce3.png"
            )
        ),
        Project(
            "Kotlin multiplatform MCQ Apps",
            "A kotlin multiplatform mcq app that uses ktor backend and authenticate users .",
            listOf("Ktor", "PostgreSQL", "Redis", "Docker"),
            ProjectType.PET_PROJECT,
            githubLink = "https://github.com/naser09/MCQ_KMP.git",
            images = listOf(
                "projects/ktor1.png",
                "projects/ktor2.png"
            )
        ),
        Project(
            "Laravel 10 with multiple authentication & authorization .",
            "A kotlin multiplatform mcq app that uses ktor backend and authenticate users .",
            listOf("Laravel 10", "PostgreSQL", "Blade Template", "PHP" , "Tailwind Css" ,"npm"),
            ProjectType.PET_PROJECT,
            githubLink = "https://github.com/naser09/laravel10_multi_auth.git",
            images = listOf(
                "projects/ktor1.png",
                "projects/ktor2.png"
            )
        ),
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
                            .toAttrs{
                                target(ATarget.Blank)
                            }
                    ) { Text("Download →") }
                }

                project.githubLink?.let {
                    A(
                        href = it,
                        attrs = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .color(if (colorMode == ColorMode.DARK) white else black)
                            .toAttrs{
                                target(ATarget.Blank)
                            }
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
        ProjectType.YOUTUBE -> if(colorMode == ColorMode.DARK) Color.rgb(150, 120, 0) else Color.rgb(205, 200, 200)
        ProjectType.IN_PROGRESS -> if(colorMode == ColorMode.DARK) Color.rgb(120, 150, 0) else Color.rgb(230, 180, 200)
    }
}