package naser09.github.io.pages.academic_page

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

private data class Milestone(
    val year: String,
    val description: String
)

// Academic Page
@Page("/academic")
@Composable
fun AcademicPage() {
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
                    "Academic Background",
                    "Learning experiences that shaped my journey",
                    colorMode
                )

                AcademicContent(colorMode)
            }
        }
    }
}

@Composable
private fun AcademicContent(colorMode: ColorMode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .maxWidth(800.px)
//            .margin(topBottom = 0.px, leftRight = auto)
            .gap(48.px)
    ) {
        // Diploma Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.px)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
                .borderRadius(8.px)
        ) {
            Column(modifier = Modifier.gap(16.px)) {
                H3(
                    attrs = Modifier
                        .fontSize(24.px)
                        .margin(0.px)
                        .toAttrs()
                ) { Text("Diploma in Computer Technology") }

                P(attrs = Modifier.margin(0.px).toAttrs()) {
                    Text("Completed with distinction, focusing on software development and computer systems.")
                }
            }
        }

        // Academic Timeline
        Column(modifier = Modifier.gap(24.px)) {
            H3(
                attrs = Modifier
                    .fontSize(24.px)
                    .margin(0.px)
                    .toAttrs()
            ) { Text("Academic Timeline") }

            AcademicTimeline(colorMode)
        }
    }
}

@Composable
private fun AcademicTimeline(colorMode: ColorMode) {
    val milestones = listOf(
        Milestone("2017", "Started Computer Technology Diploma"),
        Milestone("2018", "Completed Foundation Courses"),
        Milestone("2019", "Specialized in Software Development"),
        Milestone("2020", "Graduated with Distinction")
    )

    Column(modifier = Modifier.gap(24.px)) {
        milestones.forEach { milestone ->
            TimelineItem(milestone, colorMode)
        }
    }
}

@Composable
private fun TimelineItem(milestone: Milestone, colorMode: ColorMode) {
    Row(
        modifier = Modifier.gap(16.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Timeline dot and line
        Box(
            modifier = Modifier
                .size(16.px)
                .borderRadius(50.percent)
                .backgroundColor(if (colorMode == ColorMode.DARK) white else black)
        )

        // Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.px)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(235, 235, 235))
                .borderRadius(8.px)
        ) {
            Column(modifier = Modifier.gap(8.px)) {
                H4(
                    attrs = Modifier
                        .fontSize(18.px)
                        .margin(0.px)
                        .toAttrs()
                ) { Text(milestone.year) }

                P(
                    attrs = Modifier
                        .margin(0.px)
                        .toAttrs()
                ) { Text(milestone.description) }
            }
        }
    }
}
