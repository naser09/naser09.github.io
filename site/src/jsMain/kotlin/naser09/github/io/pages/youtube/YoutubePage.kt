package naser09.github.io.pages.youtube

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

private data class Video(
    val title: String,
    val thumbnail: String,
    val youtubeLink: String
)
// YouTube Videos Page
@Page("/video")
@Composable
fun YouTubePage() {
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
                    "Naser's Codelab",
                    "Sharing my journey and knowledge about Kotlin and Multiplatform Development",
                    colorMode
                )

                VideoGrid(colorMode)
            }
        }
    }
}

@Composable
private fun VideoGrid(colorMode: ColorMode) {
    var loadedVideos by remember { mutableStateOf(6) }
    val videos = listOf(
        Video("Title 1", "thumbnail1.jpg", "youtube_link_1"),
        // Add more videos
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(24.px).gap(32.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, sm = 2, md = 3)
        ) {
            videos.take(loadedVideos).forEach { video ->
                VideoCard(video, colorMode)
            }
        }

        if (loadedVideos < videos.size) {
            Button(
                attrs = Modifier
                    .padding(16.px)
                    .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(40, 40, 40) else Color.rgb(230, 230, 230))
                    .color(if (colorMode == ColorMode.DARK) white else black)
                    .border(0.px)
                    .borderRadius(4.px)
                    .cursor(Cursor.Pointer)
                    .onClick { loadedVideos += 6 }
                    .toAttrs()
            ) { Text("Load More") }
        }
    }
}

@Composable
private fun VideoCard(video: Video, colorMode: ColorMode) {
    Box(
        modifier = Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode == ColorMode.DARK) Color.rgb(30, 30, 30) else Color.rgb(245, 245, 245))
            .borderRadius(8.px)
//            .transition(Transition.group {
//                transform(300.ms)
//            })
//            .styleModifier {
//                CssStyle {
//                    hover {
//                        Modifier.transform { scale(1.05) }
//                    }
//                }
//            }
    ) {
        Column(modifier = Modifier.gap(16.px)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16.0/9.0)
                    .backgroundColor(Color.rgb(20, 20, 20))
                    .borderRadius(8.px)
                    .overflow(Overflow.Hidden)
            ) {
                Image(
                    src = video.thumbnail,
                    modifier = Modifier.fillMaxSize().objectFit(ObjectFit.Cover)
                )

                // Play button overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .backgroundColor(Color.rgba(0f, 0f, 0f, 0.5f))
                        .opacity(0)
                        .transition(Transition.of("opacity",300.ms))
//                        .styleModifier {
//                            CssStyle {
//                                hover {
//                                    Modifier.opacity(100)
//                                }
//                            }
//                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    // Play icon
                    Div(
                        attrs = Modifier
                            .size(48.px)
                            .borderRadius(50.percent)
                            .backgroundColor(Color.rgb(255, 0, 0))
                            .toAttrs()
                    )
                }
            }

            H3(
                attrs = Modifier
                    .fontSize(18.px)
                    .margin(0.px)
                    .padding(16.px)
                    .toAttrs()
            ) { Text(video.title) }

            A(
                href = video.youtubeLink,
                attrs = Modifier
                    .fillMaxWidth()
                    .padding(16.px)
                    .backgroundColor(Color.rgb(255, 0, 0))
                    .color(white)
                    .textAlign(TextAlign.Center)
                    .textDecorationLine(TextDecorationLine.None)
                    .borderRadius(4.px)
                    .toAttrs()
            ) { Text("Watch Now") }
        }
    }
}
