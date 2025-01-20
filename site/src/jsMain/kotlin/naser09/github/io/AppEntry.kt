package naser09.github.io

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.http.fetch
import com.varabyte.kobweb.browser.http.http
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import naser09.github.io.components.model.Version
import org.jetbrains.compose.web.css.*
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.fetch.RequestInit

private const val COLOR_MODE_KEY = "naser09.github.io:colorMode"

@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = ColorMode.loadFromLocalStorage(COLOR_MODE_KEY) ?: ColorMode.systemPreference
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    LaunchedEffect(Unit){
        try {
            val version = window.fetch("https://raw.githubusercontent.com/naser09/portfolio_data/refs/heads/main/versions.json",
                RequestInit()).await()
            if (version.ok){
                val data = Json.decodeFromString<Version>(version.text().await())
                val oldVersion = localStorage.get("database_version")?.toIntOrNull()?:-1
                if (oldVersion<data.version){
                    localStorage.clear()
                    localStorage["database_version"] = data.version.toString()
                }
            }
        }catch (ex:Exception){ }
    }
    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) {
            colorMode.saveToLocalStorage(COLOR_MODE_KEY)
        }

        Surface(
            SmoothColorStyle.toModifier()
                .minHeight(100.vh)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            content()
        }
    }
}