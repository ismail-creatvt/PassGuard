package com.ismail.creatvt.passguard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Website(
    val icon: String,
    val name: String,
    var otherName: String? = "Unknown"
) : Parcelable {

    val websiteName: String
        get() = if(name == WebsiteFactory.OTHER) otherName?:"Unknown" else name

    override fun toString(): String {
        return name
    }
}