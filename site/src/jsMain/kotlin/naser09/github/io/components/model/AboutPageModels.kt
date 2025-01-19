package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo(
    val label: String,
    val value: String
)
@Serializable
data class Skill(
    val name: String,
    val proficiency: Int, // 0-100
    val description: String
)