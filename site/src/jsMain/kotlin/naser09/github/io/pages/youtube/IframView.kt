package naser09.github.io.pages.youtube

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.Iframe

private val iframe  = """<iframe 
    |width="560" height="315"
    | src="https://www.youtube-nocookie.com/embed/RJhR9oPOrj0?si=Xq6cYoXPbrE1Z85d"
    |  title="YouTube video player"
    |   frameborder="0"
    |    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture;
    |     web-share" 
    |     referrerpolicy="strict-origin-when-cross-origin"
    |      allowfullscreen></iframe>""".trimMargin()

data class IframeProperties(
    val width: String = "560",
    val height: String = "315",
    val src: String = "",
    val title: String = "",
    val frameBorder: String = "0",
    val allow: String = "",
    val referrerPolicy: String = "",
    val allowFullscreen: Boolean = false,
    val videoId: String = ""
){
    companion object{
        fun parseIframeString(iframeString: String,defaultValue:IframeProperties = IframeProperties()): IframeProperties {
            // Regular expressions to extract attributes
            val widthRegex = """width="([^"]*)"""".toRegex()
            val heightRegex = """height="([^"]*)"""".toRegex()
            val srcRegex = """src="([^"]*)"""".toRegex()
            val titleRegex = """title="([^"]*)"""".toRegex()
            val frameborderRegex = """frameborder="([^"]*)"""".toRegex()
            val allowRegex = """allow="([^"]*)"""".toRegex()
            val referrerpolicyRegex = """referrerpolicy="([^"]*)"""".toRegex()
            val allowFullscreenRegex = """allowfullscreen""".toRegex()

            // Extract video ID from src URL
            val videoIdRegex = """/embed/([^?]*)""".toRegex()

            val videoId = videoIdRegex.find(iframeString)?.groupValues?.get(1) ?: ""

            return IframeProperties(
                width = widthRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.width,
                height = heightRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.height,
                src = srcRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.src,
                title = titleRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.title,
                frameBorder = frameborderRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.frameBorder,
                allow = allowRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.allow,
                referrerPolicy = referrerpolicyRegex.find(iframeString)?.groupValues?.get(1) ?: defaultValue.referrerPolicy,
                allowFullscreen = allowFullscreenRegex.find(iframeString) != null,
                videoId = videoId
            )
        }
    }
}



@Composable
fun VideoPlay(iframeProps: IframeProperties) {
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
                attr("src", iframeProps.src)
                attr("title", iframeProps.title)
                attr("frameborder", iframeProps.frameBorder)
                attr("allow", iframeProps.allow)
                attr("referrerpolicy", iframeProps.referrerPolicy)
                if (iframeProps.allowFullscreen) {
                    attr("allowfullscreen", "true")
                }
                style {
                    property("width", "100%")
                    property("height", "450px")
                    property("aspect-ratio", "16/9")
                }
            }
        )
    }
}