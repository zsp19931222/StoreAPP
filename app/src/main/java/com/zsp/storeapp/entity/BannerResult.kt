package com.zsp.storeapp.entity

data class BannerResult(
    val bannerId: Int,
    val bannerPath: String

) {
    override fun toString(): String {
        return "BannerResult(bannerId=$bannerId, bannerPath='$bannerPath')"
    }
}