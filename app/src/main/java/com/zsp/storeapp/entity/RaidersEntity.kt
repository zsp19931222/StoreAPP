package com.zsp.storeapp.entity

data class RaidersEntity(
    val raidersAuthor: String,
    val raidersAuthorImg: String,
    val raidersHref: String,
    val raidersId: Int,
    val raidersImg: String,
    val raidersLike: Int,
    val raidersLook: Int,
    val raidersMessage: Int,
    val raidersTime: String,
    val raidersTitle: String
) {
    override fun toString(): String {
        return "RaidersEntity(raidersAuthor='$raidersAuthor', raidersAuthorImg='$raidersAuthorImg', raidersHref='$raidersHref', raidersId=$raidersId, raidersImg='$raidersImg', raidersLike=$raidersLike, raidersLook=$raidersLook, raidersMessage=$raidersMessage, raidersTime='$raidersTime', raidersTitle='$raidersTitle')"
    }
}