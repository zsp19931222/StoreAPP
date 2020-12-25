package com.zsp.storeapp.entity

data class BannerResult(
    val id: Int,
    val path: String,
    val href: String

) {
    override fun toString(): String {
        return "BannerResult(id=$id, path='$path', href='$href')"
    }
}