package naser09.github.io.pages.about

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
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.data_layer.DataStore
import naser09.github.io.components.PageHeader
import naser09.github.io.components.model.PersonalInfo
import naser09.github.io.components.model.Skill


@Page("/about")
@Composable
fun AboutPage() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    LaunchedEffect(Unit){
        DataStore.loadPersonalInfo()
        DataStore.loadPersonalSkill()
    }
    BottomNavigationLayout {
        Box(
            Modifier
                .maxWidth(100.vw)
                .overflow(Overflow.Clip)
                .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(18, 18, 18) else Color.rgb(250, 250, 250))
                .color(if (colorMode == ColorMode.DARK) Color.rgb(240, 240, 240) else Color.rgb(33, 33, 33))
                .margin(bottom = 13.vh)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageHeader(
                    "About Me",
                    "Passionate Kotlin developer building cross-platform solutions",
                    colorMode,
                    icon = if (colorMode.isLight) "icons/identity.svg" else "icons/identity-white.svg"
                    ,""
                )
                AboutContent(colorMode, breakpoint,
                    DataStore.personalInfo.value?: emptyList(),
                    DataStore.personalSkill.value?: emptyList()
                )
            }
        }
    }
}

@Composable
private fun AboutContent(
    colorMode: ColorMode,
    breakpoint: Breakpoint,
    personalInfo: List<PersonalInfo>,
    skills: List<Skill>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(if (breakpoint >= Breakpoint.MD) 80.percent else 95.percent)
            .maxWidth(1200.px)
            .padding(24.px)
            .gap(48.px)
    ) {
        // Profile Section
        if (breakpoint >= Breakpoint.MD) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(48.px),
                verticalAlignment = Alignment.Top
            ) {
                ProfileImage()
                ProfileInfo(colorMode, personalInfo)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(32.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage()
                ProfileInfo(colorMode, personalInfo)
            }
        }

        // Skills Section
        SkillsSection(colorMode, breakpoint, skills)

        // Vision Section
        VisionSection(colorMode)
    }
}

@Composable
private fun ProfileImage() {
    Box(
        modifier = Modifier
            .size(300.px)
            .borderRadius(16.px)
            .overflow(Overflow.Hidden)
            .backgroundColor(BackgroundColor.Transparent)

           // .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.3)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
    ) {
        Image(
            src = "icons/profile.webp",
            modifier = Modifier
                .fillMaxSize()
                .objectFit(ObjectFit.Contain)
                .transition(Transition.of("transform", 300.ms))
                .styleModifier {
                    property("hover", "transform: scale(1.05)")
                }
        )
    }
}

@Composable
private fun ProfileInfo(colorMode: ColorMode, personalInfo: List<PersonalInfo>) {
    val breakpoint = rememberBreakpoint()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(255, 255, 255))
            .borderRadius(16.px)
           // .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.2)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
    ) {
        Column(modifier = Modifier.gap(24.px)) {
            H2(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(28.px)
                    .fontWeight(700)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                    .toAttrs()
            ) {
                Text("Personal Information")
            }

            personalInfo.forEach { info ->
                Row(
                    if (breakpoint>=Breakpoint.MD){
                        Modifier
                            .fillMaxWidth()
                            .padding(8.px)
                            .gap(16.px)
                    }else Modifier
                        .padding(8.px)
                        .gap(8.px)
                        .fontSize(FontSize.Small)
                ) {
                    Span(
                        attrs = Modifier
                            .minWidth(100.px)
                            .fontWeight(600)
                            .opacity(0.8)
                            .toAttrs()
                    ) { Text(info.label) }
                    Span { Text(info.value) }
                }
            }
        }
    }
}

@Composable
private fun SkillsSection(colorMode: ColorMode, breakpoint: Breakpoint, skills: List<Skill>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(255, 255, 255))
            .borderRadius(16.px)
         //   .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.2)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
    ) {
        Column(modifier = Modifier.gap(24.px)) {
            H2(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(28.px)
                    .fontWeight(700)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                    .toAttrs()
            ) {
                Text("Technical Skills")
            }

            SimpleGrid(
                modifier = Modifier.fillMaxWidth(),
                numColumns = numColumns(base = 1, md = 2)
            ) {
                skills.forEach { skill ->
                    SkillCard(skill, colorMode)
                }
            }
        }
    }
}

@Composable
private fun SkillCard(skill: Skill, colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(245, 245, 245))
            .borderRadius(12.px)
            .transition(Transition.of("transform", 300.ms))
            .styleModifier {
                property("hover", "transform: translateY(-5px)")
            }
    ) {
        Column(modifier = Modifier.padding(16.px).gap(12.px)) {
            H3(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(20.px)
                    .fontWeight(600)
                    .toAttrs()
            ) {
                Text(skill.name)
            }

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.px)
                    .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(60, 60, 60) else Color.rgb(220, 220, 220))
                    .borderRadius(4.px)
            ) {
                Box(
                    modifier = Modifier
                        .width(skill.proficiency.percent)
                        .height(8.px)
                        .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                        .borderRadius(4.px)
                )
            }

            P(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(14.px)
                    .opacity(0.8)
                    .toAttrs()
            ) {
                Text(skill.description)
            }
        }
    }
}

@Composable
private fun VisionSection(colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(255, 255, 255))
            .borderRadius(16.px)
           // .boxShadow(if (colorMode == ColorMode.DARK) "0 4px 6px rgba(0, 0, 0, 0.2)" else "0 4px 6px rgba(0, 0, 0, 0.1)")
    ) {
        Column(modifier = Modifier.gap(24.px)) {
            H2(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(28.px)
                    .fontWeight(700)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(129, 140, 248) else Color.rgb(79, 70, 229))
                    .toAttrs()
            ) {
                Text("Vision & Goals")
            }

            P(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(16.px)
                    .lineHeight(1.6)
                    .opacity(0.9)
                    .toAttrs()
            ) {
                Text(
                    "As a passionate Kotlin developer, I am dedicated to pushing the boundaries of cross-platform development. " +
                            "My goal is to create efficient, maintainable, and user-friendly applications while contributing to the developer community " +
                            "through knowledge sharing and open-source contributions. I believe in continuous learning and staying updated with " +
                            "the latest technologies to deliver innovative solutions."
                )
            }
        }
    }
}