package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class Version(
    val version:Long,
    val notificationVersion:Long,
)
