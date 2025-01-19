package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val type: ProjectType,
    val releaseLink: String? = null,
    val githubLink: String? = null,
    val images: List<String> // Updated to support multiple images
)

@Serializable
 enum class ProjectType {
    PRODUCTION, OPEN_SOURCE, PET_PROJECT, FREELANCE , YOUTUBE ,IN_PROGRESS
}
