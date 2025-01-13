package naser09.github.io.pages.projects


import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
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
import com.varabyte.kobweb.silk.style.selectors.hover
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black

private data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val playStoreLink: String,
    val githubLink: String
)

// Projects Page
@Page("/projects")
@Composable
fun ProjectsPage() {
    var colorMode by remember { mutableStateOf(ColorMode.DARK) }

    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else white)
                .color(if (colorMode == ColorMode.DARK) white else black)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                PageHeader(
                    "Projects",
                    "Showcasing my work in Android, Kotlin Multiplatform, and Backend Development",
                    colorMode
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
            "App Store App 1",
            "Description of first Play Store app",
            listOf("Kotlin", "Jetpack Compose"),
            "play_store_link_1",
            "github_link_1"
        ),
        // Add more projects
    )

    SimpleGrid(
        modifier = Modifier.fillMaxWidth().padding(24.px),
        numColumns = numColumns(base = 1, sm = 2, md = 3)
    ) {
        projects.forEach { project ->
            ProjectCard(project, colorMode)
        }
    }
}

@Composable
private fun ProjectCard(project: Project, colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(8.px)
//            .transition(Transition.group {
//                transform(300.ms)
//                boxShadow(300.ms)
//            })
//            .styleModifier {
//                CssStyle {
//                    hover {
//                        Modifier
//                            .transform { scale(1.02) }
//                            .boxShadow(0.px, 4.px, 12.px, Color.rgb(0, 0, 0, 0.1))
//                    }
//                }
//            }
    ) {
        Column(modifier = Modifier.padding(24.px).gap(16.px)) {
            H3(
                attrs = Modifier
                    .fontSize(24.px)
                    .margin(0.px)
                    .toAttrs()
            ) { Text(project.title) }

            P(attrs = Modifier.margin(0.px).toAttrs()) {
                Text(project.description)
            }

            // Technology tags
            Row(modifier = Modifier.gap(8.px)) {
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
            Row(modifier = Modifier.gap(16.px)) {
                A(
                    href = project.playStoreLink,
                    attrs = Modifier
                        .textDecorationLine(TextDecorationLine.None)
                        .color(if (colorMode == ColorMode.DARK) white else black)
                        .toAttrs()
                ) { Text("Play Store →") }

                A(
                    href = project.githubLink,
                    attrs = Modifier
                        .textDecorationLine(TextDecorationLine.None)
                        .color(if (colorMode == ColorMode.DARK) white else black)
                        .toAttrs()
                ) { Text("GitHub →") }
            }
        }
    }
}