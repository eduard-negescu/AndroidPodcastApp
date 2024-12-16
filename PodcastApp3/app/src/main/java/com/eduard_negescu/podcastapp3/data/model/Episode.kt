package com.eduard_negescu.podcastapp3.data.model

data class Episode(
    val audio: String,
    val audio_length_sec: Int,
    val description: String,
    val explicit_content: Boolean,
    val guid_from_rss: String,
    val id: String,
    val image: String,
    val link: String,
    val listennotes_edit_url: String,
    val listennotes_url: String,
    val maybe_audio_invalid: Boolean,
    val pub_date_ms: Long,
    val thumbnail: String,
    val title: String
)