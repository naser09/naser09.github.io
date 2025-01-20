package naser09.github.io.pages.tech_stack

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.data_layer.DataStore
import naser09.github.io.components.data_layer.DataStore.technologies
import naser09.github.io.components.PageHeader
import naser09.github.io.components.model.Proficiency
import naser09.github.io.components.model.TechCategory
import naser09.github.io.components.model.Technology
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black

@Page("/tech_stack")
@Composable
fun TechStackPage() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    LaunchedEffect(Unit){
        DataStore.loadTechStack()
    }
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
                    "Tech Stack",
                    "My Technical Expertise & Tools",
                    colorMode,
                    if (colorMode.isLight) "icons/gear.svg" else "icons/gear-white.svg",
                    ""
                )

                TechStackContent(colorMode, breakpoint)
            }
        }
    }
}

@Composable
private fun TechStackContent(colorMode: ColorMode, breakpoint: Breakpoint) {
    var selectedCategory by remember { mutableStateOf<TechCategory?>(null) }
    var selectedProficiency by remember { mutableStateOf<Proficiency?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                left = if (breakpoint > Breakpoint.SM) 48.px else 16.px,
                right = if (breakpoint > Breakpoint.SM) 48.px else 16.px,
                top = 32.px,
                bottom = 32.px
            )
            .gap(32.px)
    ) {
        // Filters
        Filters(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            selectedProficiency = selectedProficiency,
            onProficiencySelected = { selectedProficiency = it },
            colorMode = colorMode
        )
        HorizontalDivider(Modifier.fillMaxWidth().height(1.vh))
        // Tech Grid
        val filteredTech = technologies.value?.filter { tech ->
            (selectedCategory == null || tech.category == selectedCategory) &&
                    (selectedProficiency == null || tech.proficiency == selectedProficiency)
        }?: emptyList()

        TechGrid(
            technologies = filteredTech,
            colorMode = colorMode,
            breakpoint = breakpoint
        )
    }
}

@Composable
private fun Filters(
    selectedCategory: TechCategory?,
    onCategorySelected: (TechCategory?) -> Unit,
    selectedProficiency: Proficiency?,
    onProficiencySelected: (Proficiency?) -> Unit,
    colorMode: ColorMode
) {
    Column(
        modifier = Modifier.gap(16.px)
    ) {
        // Category Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
                .overflow(Overflow.Auto)
                .gap(12.px)
                .padding(bottom = 8.px)
                .styleModifier {
                    property("scrollbar-width", "none")
                    property("-ms-overflow-style", "none")
                    property("&::-webkit-scrollbar", "{ display: none; }")
                }
        ) {
            FilterButton(
                text = "All Categories",
                isSelected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                colorMode = colorMode
            )

            TechCategory.entries.forEach { category ->
                FilterButton(
                    text = category.displayName,
                    isSelected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    colorMode = colorMode
                )
            }
        }

        // Proficiency Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .flexFlow(FlexDirection.Row, FlexWrap.Wrap)
                .overflow(Overflow.Auto)
                .gap(12.px)
                .styleModifier {
                    property("scrollbar-width", "none")
                    property("-ms-overflow-style", "none")
                    property("&::-webkit-scrollbar", "{ display: none; }")
                }
        ) {
            FilterButton(
                text = "All Levels",
                isSelected = selectedProficiency == null,
                onClick = { onProficiencySelected(null) },
                colorMode = colorMode
            )

            Proficiency.entries.forEach { proficiency ->
                FilterButton(
                    text = proficiency.displayName,
                    isSelected = proficiency == selectedProficiency,
                    onClick = { onProficiencySelected(proficiency) },
                    colorMode = colorMode,
                    backgroundColor = if (proficiency == selectedProficiency) {
                        if (colorMode == ColorMode.DARK) proficiency.darkColor.toRGBA() else proficiency.lightColor.toRGBA()
                    } else null
                )
            }
        }
    }
}

@Composable
private fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    colorMode: ColorMode,
    backgroundColor: Color? = null
) {
    Button(
        attrs = Modifier
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = if (colorMode == ColorMode.DARK) Color.rgb(75, 75, 75) else Color.rgb(200, 200, 200)
            )
            .borderRadius(20.px)
            .padding(8.px, 16.px)
            .backgroundColor(
                backgroundColor ?: if (isSelected) {
                    if (colorMode == ColorMode.DARK) Color.rgb(50, 50, 50) else Color.rgb(235, 235, 235)
                } else {
                    if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(255, 255, 255)
                }
            )
            .color(if (colorMode == ColorMode.DARK) white else black)
            .cursor(Cursor.Pointer)
            .styleModifier {
                property("transition", "all 0.3s ease")
            }
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(text)
    }
}

@Composable
private fun TechGrid(
    technologies: List<Technology>,
    colorMode: ColorMode,
    breakpoint: Breakpoint
) {
    SimpleGrid(
        modifier = Modifier.fillMaxWidth().gap(24.px),
        numColumns = numColumns(base = 1, sm = 1, md = 2, lg = 4)
    ) {
        technologies.forEach { tech ->
            TechCard(tech, colorMode)
        }
    }
}

@Composable
private fun TechCard(
    tech: Technology,
    colorMode: ColorMode
) {
    val breakpoint = rememberBreakpoint()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(12.px)
            .styleModifier {
                property("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)")
                property("transition", "all 0.3s ease")
            },
        contentAlignment = Alignment.TopCenter
//            .hover(
//                Modifier.styleModifier {
//                    property("transform", "translateY(-5px)")
//                    property("box-shadow", "0 10px 20px rgba(0, 0, 0, 0.2)")
//                }
//            )
    ) {
        Column(
            modifier = Modifier.padding(24.px).gap(16.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tech Icon
            TechnologyImage(
                technology = tech,
                colorMode = colorMode,
                breakpoint = breakpoint
            )
            // Tech Name
            H3(
                attrs = Modifier
                    .fontSize(20.px)
                    .margin(0.px)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text(tech.name) }

            // Description
            P(
                attrs = Modifier
                    .margin(0.px)
                    .fontSize(14.px)
                    .textAlign(TextAlign.Center)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(200, 200, 200) else Color.rgb(100, 100, 100))
                    .toAttrs()
            ) {
                Text(tech.description)
            }

            // Experience Years
            Span(
                attrs = Modifier
                    .fontSize(14.px)
                    .color(if (colorMode == ColorMode.DARK) Color.rgb(150, 150, 150) else Color.rgb(120, 120, 120))
                    .toAttrs()
            ) {
                Text("${tech.experienceYears} years")
            }

            // Proficiency Badge
            Box(
                modifier = Modifier
                    .padding(4.px, 8.px)
                    .backgroundColor(getProficiencyColor(tech.proficiency,colorMode.isDark))
                    .borderRadius(20.px)
            ) {
                Text(tech.proficiency.displayName)
            }
        }
    }
}


@Composable
private fun TechnologyImage(
    technology: Technology,
    colorMode: ColorMode,
    breakpoint: Breakpoint,
    modifier: Modifier = Modifier
) {
    // Dynamic sizes based on breakpoint
    val containerSize = when {
        breakpoint > Breakpoint.MD -> 64.px
        breakpoint > Breakpoint.SM -> 56.px
        else -> 48.px
    }

    val imageSize = when {
        breakpoint > Breakpoint.MD -> 40.px
        breakpoint > Breakpoint.SM -> 32.px
        else -> 24.px
    }

    Box(
        modifier = modifier
            .size(containerSize)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(235, 235, 235))
            .borderRadius(12.px)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)
    ) {
        when {
            technology.svg != null -> {
                Svg(
                    attrs = {
                        attr("viewBox", technology.svg.viewBox)
                        attr("width", imageSize.toString())
                        attr("height", imageSize.toString())
                        attr("fill", "#C3144")
                        technology.svg.stroke?.let {
                            attr("stroke", it)
                        }
                        technology.svg.strokeWidth?.let {
                            attr("stroke-width", it.toString())
                        }
                    }
                ) {
                    Path(
                        attrs = {
                            attr("d", technology.svg.d)
                        }
                    )
                }
            }
            technology.iconUrl != null -> {
                Image(
                    src = technology.iconUrl,
                    modifier = Modifier.size(imageSize)
                )
            }
            else -> {
                Image(
                    src = technology.iconPath ?: "/icons/${technology.name.lowercase()}.svg",
                    modifier = Modifier.size(imageSize)
                )
            }
        }
    }
}
private fun getProficiencyColor(proficiency: Proficiency,isDark:Boolean): Color {
    return when(proficiency){
        Proficiency.EXPERT -> if (isDark) Proficiency.EXPERT.darkColor.toRGBA() else Proficiency.EXPERT.lightColor.toRGBA()
        Proficiency.ADVANCED -> if (isDark) Proficiency.ADVANCED.darkColor.toRGBA() else Proficiency.ADVANCED.lightColor.toRGBA()
        Proficiency.INTERMEDIATE -> if (isDark) Proficiency.INTERMEDIATE.darkColor.toRGBA() else Proficiency.INTERMEDIATE.lightColor.toRGBA()
        Proficiency.BEGINNER -> if (isDark) Proficiency.BEGINNER.darkColor.toRGBA() else Proficiency.BEGINNER.lightColor.toRGBA()
    }
}