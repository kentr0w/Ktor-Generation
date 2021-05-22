package org.dk.model

import kotlinx.serialization.Serializable

@Serializable
data class GlobalConfig(val config: List<Titles>)

@Serializable
data class Titles(val title: String, val value: String)