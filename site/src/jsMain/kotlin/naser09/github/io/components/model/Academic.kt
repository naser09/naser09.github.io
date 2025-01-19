package naser09.github.io.components.model

import kotlinx.serialization.Serializable


@Serializable
data class Academic(
    val years: String,
    val name: String,
    val institute: String,
    val instituteLocation: String,
    val subjects: List<String>,
    val cgpa: String,
    val description: String,
    val roleNumber: String,
    val verifyResultLink: String
)