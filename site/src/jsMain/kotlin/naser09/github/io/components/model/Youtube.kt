package naser09.github.io.components.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoItem(
    val srcId:String,
    val title: String,
    val category:String, //or playlist name .
    val fullSrcUrl:String?=null,
    val width: String?=null,
    val height: String? = null
)