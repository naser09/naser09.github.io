package naser09.github.io.pages.tech_stack

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
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.icons.fa.*
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.PageHeader
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black

private data class Technology(
    val name: String,
    val proficiency: Proficiency,
    val category: TechCategory,
    val description: String,
    val experienceYears: Float,
    val iconPath: String? = null,
    val fontAwesomeIcon:(@Composable (size:IconSize)->Unit)? = null
)

private enum class TechCategory(val displayName: String) {
    LANGUAGES("Languages"),
    MOBILE("Mobile"),
    BACKEND("Backend"),
    DATABASE("Database"),
    CLOUD("Cloud & DevOps"),
    FRONTEND("Frontend"),
    TOOLS("Tools")
}

private enum class Proficiency(val displayName: String, val darkColor: Color, val lightColor: Color) {
    EXPERT("Expert",
        darkColor = Color.rgba(22, 163, 74, 0.3f),
        lightColor = Color.rgba(22, 163, 74, 0.2f)
    ),
    ADVANCED("Advanced",
        darkColor = Color.rgba(234, 88, 12, 0.3f),
        lightColor = Color.rgba(234, 88, 12, 0.2f)
    ),
    INTERMEDIATE("Intermediate",
        darkColor = Color.rgba(234, 179, 8, 0.3f),
        lightColor = Color.rgba(234, 179, 8, 0.2f)
    ),
    BEGINNER("Learning",
        darkColor = Color.rgba(59, 130, 246, 0.3f),
        lightColor = Color.rgba(59, 130, 246, 0.2f)
    )
}

private val technologies = listOf(
    // Languages
    Technology(
        "Kotlin",
        Proficiency.EXPERT,
        TechCategory.LANGUAGES,
        "Primary language for Android, KMP, and backend development. Extensive experience in coroutines and flow.",
        5.5f
    ),
    Technology(
        "Java",
        Proficiency.ADVANCED,
        TechCategory.LANGUAGES,
        "Used for Android development and backend services. Strong knowledge of Java 8+ features.",
        2.0f,
        fontAwesomeIcon = { FaJava(size = it) }
    ),
    Technology(
        "Python",
        Proficiency.INTERMEDIATE,
        TechCategory.LANGUAGES,
        "Basic problem solving and a single django project",
        0.6f,
        fontAwesomeIcon = { FaPython(size = it) }
    ),
    Technology(
        "Swift",
        Proficiency.BEGINNER,
        TechCategory.LANGUAGES,
        "Basic problem solving and some api that required in KMP projects",
        0.6f,
        fontAwesomeIcon = { FaSwift(size = it) }
    ),
    Technology(
        "PHP",
        Proficiency.INTERMEDIATE,
        TechCategory.LANGUAGES,
        "Basic knowledge about PHP",
        0.8f,
        fontAwesomeIcon = { FaPhp(size = it) }
    ),

    // Mobile Development
    Technology(
        "Jetpack Compose",
        Proficiency.EXPERT,
        TechCategory.MOBILE,
        "Modern Android UI development with declarative paradigm. Built multiple production apps.",
        3.0f,
        iconPath = "icons/compose.webp"
    ),
    Technology(
        "Swift UI",
        Proficiency.BEGINNER,
        TechCategory.MOBILE,
        "Modern iOS UI development with declarative paradigm. Built basic apps with it.",
        3.0f,
        fontAwesomeIcon = { FaSwift(size = it) }
    ),
    Technology(
        "Android SDK",
        Proficiency.EXPERT,
        TechCategory.MOBILE,
        "Comprehensive knowledge of Android framework, lifecycle, and architecture components.",
        5.5f,
        fontAwesomeIcon = { FaAndroid(size = it) }
    ),
    Technology(
        "KMP",
        Proficiency.EXPERT,
        TechCategory.MOBILE,
        "Kotlin Multiplatform for shared logic between Android and iOS applications.",
        2.5f,
        iconPath = "icons/kotlin.svg"
    ),

    // Backend
    Technology(
        "Ktor",
        Proficiency.EXPERT,
        TechCategory.BACKEND,
        "Building scalable backend services and RESTful APIs with Kotlin.",
        3.5f,
        fontAwesomeIcon = { FaServer(size = it) }
    ),
    Technology(
        "Laravel",
        Proficiency.INTERMEDIATE,
        TechCategory.BACKEND,
        "Java-based framework for creating microservices and web applications.",
        3.0f,
        fontAwesomeIcon = { FaLaravel(size = it) }
    ),
    Technology(
        "GraphQL",
        Proficiency.INTERMEDIATE,
        TechCategory.BACKEND,
        "API development with GraphQL for flexible data querying.",
        1.5f
    ),

    // Database
    Technology(
        "PostgreSQL",
        Proficiency.INTERMEDIATE,
        TechCategory.DATABASE,
        "Primary database for production applications. Experience with performance optimization.",
        4.0f,
        iconPath = "icons/sql.svg"
    ),
    Technology(
        "MySQL",
        Proficiency.ADVANCED,
        TechCategory.DATABASE,
        "Primary database for production applications. Experience with performance optimization.",
        4.0f,
        iconPath = "icons/sql.svg"
    ),
    Technology(
        "MongoDB",
        Proficiency.INTERMEDIATE,
        TechCategory.DATABASE,
        "NoSQL database for flexible data storage needs.",
        2.0f,
        iconPath = "icons/db.svg"
    ),
    Technology(
        "Redis",
        Proficiency.ADVANCED,
        TechCategory.DATABASE,
        "In-memory data structure store used for caching and real-time data.",
        3.0f,
        iconPath = "icons/db.svg"
    ),

    // Cloud & DevOps
    Technology(
        "Docker",
        Proficiency.BEGINNER,
        TechCategory.CLOUD,
        "Containerization for microservices and application deployment.",
        3.0f,
        fontAwesomeIcon = { FaDocker(size = it) }
    ),
    Technology(
        "Github Actions",
        Proficiency.BEGINNER,
        TechCategory.CLOUD,
        "Understand and to the bare minimum about GitHub Actions. check this website repo > kobweb_latest branch .",
        2.0f,
        fontAwesomeIcon = { FaGithub(size = it) }
    ),
    Technology(
        "VPS Server",
        Proficiency.ADVANCED,
        TechCategory.CLOUD,
        "Have Experience on running a vps server with Debian and ubuntu.",
        3.5f,
        fontAwesomeIcon = { FaLinux(size = it) }
    ),

    // Frontend
    Technology(
        "HTML/CSS",
        Proficiency.ADVANCED,
        TechCategory.FRONTEND,
        "Web development fundamentals with modern CSS features.",
        4.0f,
        fontAwesomeIcon = { FaHtml5(size = it) }
    ),

    // Tools
    Technology(
        "Git",
        Proficiency.INTERMEDIATE,
        TechCategory.TOOLS,
        "Version control and collaboration with Git and GitHub.",
        5.0f,
        fontAwesomeIcon = { FaGit(size = it) }
    ),
    Technology(
        "CI/CD",
        Proficiency.BEGINNER,
        TechCategory.TOOLS,
        "Automated testing and deployment pipelines with Jenkins and GitHub Actions.",
        3.5f,
        fontAwesomeIcon = { FaRobot(size = it) }
    ),
    Technology(
        "Gradle",
        Proficiency.EXPERT,
        TechCategory.TOOLS,
        "Build automation and dependency management for JVM projects.",
        4.0f
    )
)

@Page("/tech_stack")
@Composable
fun TechStackPage() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

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
        val filteredTech = technologies.filter { tech ->
            (selectedCategory == null || tech.category == selectedCategory) &&
                    (selectedProficiency == null || tech.proficiency == selectedProficiency)
        }

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
                        if (colorMode == ColorMode.DARK) proficiency.darkColor else proficiency.lightColor
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
            Box(
                modifier = Modifier
                    .size(56.px)
                    .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(235, 235, 235))
                    .borderRadius(12.px)
                    .display(DisplayStyle.Flex)
                    .alignItems(org.jetbrains.compose.web.css.AlignItems.Center)
                    .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            ) {
                if (tech.fontAwesomeIcon!=null){
                    tech.fontAwesomeIcon.invoke(if (breakpoint>Breakpoint.MD) IconSize.XL else IconSize.LG)
                }else{
                    Image(
                        src = tech.iconPath ?: "/icons/${tech.name.lowercase()}.svg",
                        modifier = Modifier.size(32.px)
                    )
                }
            }

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

private fun getProficiencyColor(proficiency: Proficiency,isDark:Boolean): Color {
    return when(proficiency){
        Proficiency.EXPERT -> if (isDark) Proficiency.EXPERT.darkColor else Proficiency.EXPERT.lightColor
        Proficiency.ADVANCED -> if (isDark) Proficiency.ADVANCED.darkColor else Proficiency.ADVANCED.lightColor
        Proficiency.INTERMEDIATE -> if (isDark) Proficiency.INTERMEDIATE.darkColor else Proficiency.INTERMEDIATE.lightColor
        Proficiency.BEGINNER -> if (isDark) Proficiency.BEGINNER.darkColor else Proficiency.BEGINNER.lightColor
    }
}