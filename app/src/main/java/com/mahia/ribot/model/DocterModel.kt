package com.mahia.ribot.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocterModel(
    val id: String = "",
    val field: String = "",
    val name: String = "",
    val location: String,
    val nameLocation: String
) : Parcelable
