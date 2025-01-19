package naser09.github.io.pages.youtube

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.dom.ElementRefScope
import com.varabyte.kobweb.compose.dom.registerRefScope
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
import com.varabyte.kobweb.silk.components.icons.fa.FaYoutube
import com.varabyte.kobweb.silk.style.CssRule
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.cssRules
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import naser09.github.io.components.BottomNavigationLayout
import naser09.github.io.components.DataStore
import naser09.github.io.components.PageHeader
import naser09.github.io.components.model.VideoItem
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Color.white
import org.jetbrains.compose.web.css.Color.black
import org.w3c.dom.Element
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.css.CSSRule
import kotlin.time.Duration.Companion.seconds


@Page("/video")
@Composable
fun YouTubePage() {
    var colorMode by ColorMode.currentState
    var searchQuery by remember { mutableStateOf("") }
    val breakpoint = rememberBreakpoint()

    LaunchedEffect(Unit){
        DataStore.loadVideos()
    }
    // Theme colors
    val primaryColor = if (colorMode == ColorMode.DARK) {
        Color.rgb(18, 18, 18)
    } else {
        Color.rgb(250, 250, 250)
    }

    val secondaryColor = if (colorMode == ColorMode.DARK) {
        Color.rgb(30, 30, 30)
    } else {
        Color.rgb(240, 240, 240)
    }

    val accentColor = Color.rgb(255, 0, 0)
    val textColor = if (colorMode == ColorMode.DARK) white else black

    // Filter videos based on search query
    val filteredVideos = remember(searchQuery,DataStore.videos.value) {
        if (searchQuery.isEmpty()) {
            DataStore.videos.value?: emptyList()
        } else {
            DataStore.videos.value?.filter { video ->
                video.title.contains(searchQuery, ignoreCase = true) ||
                        video.category.contains(searchQuery, ignoreCase = true)
            }?: emptyList()
        }
    }

    // Group videos by category
    val groupedVideos = remember(filteredVideos) {
        filteredVideos.groupBy { it.category }
    }

    BottomNavigationLayout {
        Box(
            Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .backgroundColor(primaryColor)
                .color(textColor)
                .margin(bottom = 13.vh)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header with Theme Toggle
                Box(
                    Modifier
                        .fillMaxWidth()
                        .backgroundColor(secondaryColor)
                        .padding(16.px)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PageHeader(
                            "Naser's Codelab",
                            "Sharing my journey and knowledge about Kotlin and Multiplatform Development",
                            colorMode,
                            "icons/youtube.svg",
                            "https://www.youtube.com/@nCodeLab"
                        )
                    }
                }

                // Search Bar
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.px)
                ) {
                    Input(
                        attrs = {
                            style {
                                width(100.percent)
                                padding(12.px)
                                fontSize(16.px)
                                borderRadius(8.px)
                                border(1.px, LineStyle.Solid, if (colorMode == ColorMode.DARK) Color.rgb(50, 50, 50) else Color.rgb(200, 200, 200))
                                backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else white)
                                color(textColor)
                            }
                            placeholder("Search videos by title or category...")
                            onInput { event ->
                                searchQuery = event.target.value
                            }
                        },
                        type = InputType.Text
                    )
                }

                // Video Grid by Category
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.px)
                        .gap(32.px)
                ) {
                    groupedVideos.forEach { (category, videos) ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .margin(bottom = 32.px)
                        ) {
                            // Category Header
                            H2(
                                attrs = {
                                    style {
                                        color(textColor)
                                        marginBottom(16.px)
                                    }
                                }
                            ) {
                                Text(category)
                            }

                            // Videos in category
                            SimpleGrid(
                                modifier = Modifier.fillMaxWidth(),
                                numColumns = numColumns(base = 1, sm = 2, md = 3, lg = 4)
                            ) {
                                videos.forEach { video ->
                                    VideoCard(video, secondaryColor, accentColor, textColor, colorMode)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoCard(
    video: VideoItem,
    backgroundColor: Color,
    accentColor: Color,
    textColor: CSSColorValue,
    colorMode: ColorMode
) {
    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(16.px)
            .backgroundColor(backgroundColor)
            .borderRadius(8.px)
            .transition(Transition.of("transform", 300.ms))
            .animation(Animation.of("translate",1000.ms))
    ) {
        Column {
            VideoPlay(
                videoId = video.srcId,
                fullVideoUrl = video.fullSrcUrl,
                width = video.width,
                height = video.height
            )

            H3(
                attrs = {
                    style {
                        color(textColor)
                        marginTop(8.px)
                        marginBottom(4.px)
                    }
                }
            ) {
                Text(video.title)
            }

            Span(
                attrs = {
                    style {
                        color(if (colorMode == ColorMode.DARK) Color.rgb(170, 170, 170) else Color.rgb(100, 100, 100))
                        fontSize(14.px)
                    }
                }
            ) {
                Text(video.category)
            }
        }
    }
}


@Composable
fun VideoPlay(
    videoId: String = "RJhR9oPOrj0",
    fullVideoUrl:String?=null,
    width:String?=null,
    height: String?=null
) {
    Box(
        modifier = Modifier
            .margin(top = 20.px)
            .margin(bottom = 20.px)
            .maxWidth(800.px)
            .padding(10.px)
            .borderRadius(8.px)
            .boxShadow(offsetX = 0.px, offsetY = 4.px, blurRadius = 8.px, color = rgba(0, 0, 0, 0.1))
    ) {
        Iframe(
            attrs = {
                attr("src",fullVideoUrl?:"https://www.youtube-nocookie.com/embed/$videoId")
                width?.let { attr("width", it) }
                height?.let { attr("height", it) }
                attr("title", "YouTube video player")
                attr("frameborder", "0")
                attr("allow", "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share")
                attr("allowfullscreen", "true")
                attr("referrerpolicy", "strict-origin-when-cross-origin")
                style {
                    property("aspect-ratio", "16/9")
                }
            }
        )
    }
}