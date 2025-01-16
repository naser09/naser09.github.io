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
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target

private data class Academic(
    val years: String,
    val name: String,
    val institute: String,
    val instituteLocation: String,
    val subjects: List<String>,
    val cgpa: String,
    val description: String,
    val roleNumber: String,
    val verifyResultLink: String
)

@Page("/academic")
@Composable
fun AcademicPage() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

    val academics = remember {
        listOf(
            Academic(
                years = "2018-2021",
                name = "Diploma in Computer Technology",
                institute = "Cumilla Polytechnic Institute",
                instituteLocation = "Cumilla, Bangladesh",
                subjects = listOf("Programming", "Database Management", "Web Development", "Computer Networks"),
                cgpa = "3.56 out of 4.0",
                description = "Specialized in software development with focus on modern programming languages and web technologies.",
                roleNumber = "role: 937535 year: 2021 Exam-Type: Diploma in Engineering",
                verifyResultLink = "http://180.211.162.102:8444/result_arch/"
            ),
            Academic(
                years = "2017-2018",
                name = "Higher Secondary Certificate/HSC/Alim",
                institute = "Sadipur Islamia Senior Alim Madrasah",
                instituteLocation = "Sonargoan,Narayanganj,Dhaka",
                subjects = listOf("Arabic", "Bangla", "History", "Mathematics","General Study"),
                cgpa = "4.65 out of 5.0",
                description = "Completed HSC with distinction in Science group, focusing on computer science and mathematics.",
                roleNumber = "HSC14-456",
                verifyResultLink = "http://www.educationboardresults.gov.bd"
            ),
            Academic(
                years = "2015-2016",
                name = "SSC/Dhakil Examination",
                institute = "Sadipur Islamia Senior Alim Madrasah",
                instituteLocation = "Sonargoan,Narayanganj,Dhaka",
                subjects = listOf("Arabic", "Bangla", "History", "Mathematics","General Study"),
                cgpa = "4.45 out of 5.0",
                description = "Completed Dhakil Examination in 2015-16 sessions .",
                roleNumber = "role:104025",
                verifyResultLink = "http://www.educationboardresults.gov.bd"
            )
        )
    }

    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else Color.rgb(250, 250, 250))
                .color(if (colorMode == ColorMode.DARK) Color.rgb(240, 240, 240) else Color.rgb(33, 33, 33))
                .margin(bottom = 13.vh)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageHeader(
                    "Academic Journey",
                    "A timeline of my educational achievements and experiences",
                    colorMode,
                    "icons/book.svg",
                    ""
                )

                AcademicContent(academics, colorMode, breakpoint)
            }
        }
    }
}

@Composable
private fun AcademicContent(
    academics: List<Academic>,
    colorMode: ColorMode,
    breakpoint: Breakpoint
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(if (breakpoint >= Breakpoint.MD) 80.percent else 95.percent)
            .maxWidth(1000.px)
            .padding(24.px)
            .gap(32.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Summary Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.px)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
                .borderRadius(12.px)
//                .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.2)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
        ) {
            Column(modifier = Modifier.gap(16.px)
                .alignContent(org.jetbrains.compose.web.css.AlignContent.Center)
                .alignItems(com.varabyte.kobweb.compose.css.AlignItems.Center),
                horizontalAlignment = Alignment.CenterHorizontally) {
                H2(
                    attrs = Modifier
                        .fontSize(if (breakpoint >= Breakpoint.MD) 32.px else 24.px)
                        .margin(0.px)
                        .fontWeight(700)
                        .toAttrs()
                ) { Text("Educational Background") }

                P(
                    attrs = Modifier
                        .margin(0.px)
                        .fontSize(if (breakpoint >= Breakpoint.MD) 18.px else 16.px)
                        .lineHeight(1.6)
                        .opacity(0.9)
                        .toAttrs()
                ) {
                    Text("My academic journey reflects a strong foundation in technology and continuous learning.")
                }
            }
        }

        // Academic Timeline
        Column(modifier = Modifier.gap(32.px), horizontalAlignment = Alignment.CenterHorizontally) {
            academics.forEach { academic ->
                AcademicCard(academic, colorMode, breakpoint)
            }
        }
    }
}

@Composable
private fun AcademicCard(
    academic: Academic,
    colorMode: ColorMode,
    breakpoint: Breakpoint
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(35, 35, 35) else Color.rgb(255, 255, 255))
            .borderRadius(16.px)
//            .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.2)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
            .transition(Transition.of("transform", 300.ms))
            .styleModifier {
                property("hover", "transform: translateY(-5px)")
            }
    ) {
        Column(modifier = Modifier.gap(16.px)) {
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    H3(
                        attrs = Modifier
                            .fontSize(if (breakpoint >= Breakpoint.MD) 24.px else 20.px)
                            .margin(0.px)
                            .fontWeight(700)
                            .color(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                            .toAttrs()
                    ) { Text(academic.name) }

                    Span(
                        attrs = Modifier
                            .fontSize(14.px)
                            .opacity(0.8)
                            .toAttrs()
                    ) { Text(academic.years) }
                }

                Box(
                    modifier = Modifier
                        .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(45, 45, 45) else Color.rgb(243, 244, 246))
                        .padding(8.px, 16.px)
                        .borderRadius(20.px)
                ) {
                    Text(academic.cgpa)
                }
            }

            // Institute Info
            P(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(18.px)
                    .fontWeight(500)
                    .toAttrs()
            ) {
                Text("${academic.institute}, ${academic.instituteLocation}")
            }

            // Description
            P(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(16.px)
                    .lineHeight(1.6)
                    .opacity(0.9)
                    .toAttrs()
            ) {
                Text(academic.description)
            }

            // Subjects
            if (breakpoint >= Breakpoint.MD) {
                SimpleGrid(
                    modifier = Modifier.fillMaxWidth().gap(8.px),
                    numColumns = numColumns(base = 2, md = 3)
                ) {
                    academic.subjects.forEach { subject ->
                        Box(
                            modifier = Modifier
                                .padding(8.px, 12.px)
                                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(45, 45, 45) else Color.rgb(243, 244, 246))
                                .borderRadius(8.px)
                        ) {
                            Text(subject)
                        }
                    }
                }
            } else {
                Column(modifier = Modifier.gap(8.px)) {
                    academic.subjects.forEach { subject ->
                        Box(
                            modifier = Modifier
                                .padding(8.px, 12.px)
                                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(45, 45, 45) else Color.rgb(243, 244, 246))
                                .borderRadius(8.px)
                        ) {
                            Text(subject)
                        }
                    }
                }
            }

            // Verification Link
            A(
                href = academic.verifyResultLink,
                attrs = Modifier
                    .fontSize(14.px)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                    .textDecorationLine(TextDecorationLine.None)
                    .toAttrs {
                        target(ATarget.Blank)
                    }
            ) {
                Text("Verify Result (Roll: ${academic.roleNumber})")
            }
        }
    }
}