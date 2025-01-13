package naser09.github.io.pages.tech_stack

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

private data class Technology(
    val name: String,
    val proficiency: String,
    val category: String
)

// Tech Stack Page
@Page("/tech_stack")
@Composable
fun TechStackPage() {
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
                    "Tech Stack",
                    "Technologies I've worked with",
                    colorMode
                )

                TechStackGrid(colorMode)
            }
        }
    }
}

@Composable
private fun TechStackGrid(colorMode: ColorMode) {
    val technologies = listOf(
        Technology("Kotlin", "Expert", "Core"),
        Technology("Jetpack Compose", "Expert", "Core"),
        Technology("Ktor", "Expert", "Core"),
        Technology("Redis", "Intermediate", "Core"),
        Technology("GraphQL", "Intermediate", "Core"),
        Technology("HTML", "Intermediate", "Additional"),
        Technology("CSS", "Intermediate", "Additional"),
        Technology("JavaScript", "Beginner", "Additional"),
        Technology("Python + Django", "Beginner", "Additional"),
        Technology("Laravel", "Beginner", "Additional")
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(24.px).gap(48.px)
    ) {
        technologies.groupBy { it.category }.forEach { (category, techList) ->
            Column(modifier = Modifier.gap(24.px)) {
                H2(
                    attrs = Modifier
                        .fontSize(32.px)
                        .margin(0.px)
                        .toAttrs()
                ) { Text(category) }

                SimpleGrid(
                    modifier = Modifier.fillMaxWidth(),
                    numColumns = numColumns(base = 1, sm = 2, md = 3)
                ) {
                    techList.forEach { tech ->
                        TechCard(tech, colorMode)
                    }
                }
            }
        }
    }
}
@Composable
private fun TechCard(tech: Technology, colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(8.px)
//            .transition(Transition.group {
//                transform(300.ms)
//                backgroundColor(300.ms)
//            })
//            .styleModifier {
//                CssStyle {
//                    hover {
//                        Modifier
//                            .transform { scale(1.05) }
//                            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(235, 235, 235))
//                    }
//                }
//            }
    ) {
        Column(
            modifier = Modifier.padding(24.px).gap(16.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tech Icon
            Box(
                modifier = Modifier
                    .size(48.px)
                    .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(235, 235, 235))
                    .borderRadius(8.px)
            ) {
                Image(
                    src = "/icons/${tech.name.lowercase()}.svg",
                    modifier = Modifier.size(32.px)
                )
            }

            // Tech Name
            H3(
                attrs = Modifier
                    .fontSize(20.px)
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text(tech.name) }

            // Proficiency Badge
            Box(
                modifier = Modifier
                    .padding(4.px, 8.px)
                    .backgroundColor(getProficiencyColor(tech.proficiency))
                    .borderRadius(4.px)
            ) {
                Text(tech.proficiency)
            }
        }
    }
}

private fun getProficiencyColor(proficiency: String): Color {
    return when (proficiency) {
        "Expert" -> Color.rgb(22, 163, 74)      // Green
        "Intermediate" -> Color.rgb(234, 179, 8) // Yellow
        else -> Color.rgb(59, 130, 246)         // Blue
    }
}

