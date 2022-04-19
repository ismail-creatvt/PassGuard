package com.ismail.creatvt.passguard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Website(
    val icon: String,
    val name: String
):Parcelable {
    override fun toString(): String {
        return name
    }
}