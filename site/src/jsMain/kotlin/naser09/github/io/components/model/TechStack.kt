package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class Technology(
    val name: String,
    val proficiency: Proficiency,
    val category: TechCategory,
    val description: String,
    val experienceYears: Float,
    val iconPath: String? = null,
    val iconUrl:String?=null,
    val svg: Svg?=null
)

@Serializable
data class Svg(
    val viewBox: String,
    val d: String,
    val fill: String? = null,
    val stroke: String? = null,
    val strokeWidth: Float? = null,
)
@Serializable
enum class TechCategory(val displayName: String) {
    LANGUAGES("Languages"),
    MOBILE("Mobile"),
    BACKEND("Backend"),
    DATABASE("Database"),
    CLOUD("Cloud & DevOps"),
    FRONTEND("Frontend"),
    TOOLS("Tools")
}
@Serializable
data class Color(
    val red:Int,
    val green:Int,
    val blue:Int,
    val alpha:Float
){
    fun toRGBA() = com.varabyte.kobweb.compose.ui.graphics.Color.rgba(red,green,blue,alpha)
}
@Serializable
enum class Proficiency(val displayName: String, val darkColor: Color, val lightColor: Color) {
    EXPERT("Expert",
        darkColor = Color(22, 163, 74, 0.3f),
        lightColor = Color(22, 163, 74, 0.2f)
    ),
    ADVANCED("Advanced",
        darkColor = Color(234, 88, 12, 0.3f),
        lightColor = Color(234, 88, 12, 0.2f)
    ),
    INTERMEDIATE("Intermediate",
        darkColor = Color(234, 179, 8, 0.3f),
        lightColor = Color(234, 179, 8, 0.2f)
    ),
    BEGINNER("Learning",
        darkColor = Color(59, 130, 246, 0.3f),
        lightColor = Color(59, 130, 246, 0.2f)
    )
}
