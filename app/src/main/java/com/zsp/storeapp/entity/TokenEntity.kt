package com.zsp.storeapp.entity

/**
 * description:
 * author:created by Andy on 2020/12/4 0004 17:15
 * email:zsp872126510@gmail.com
 */
data class TokenEntity(
    var token: String
) {
    override fun toString(): String {
        return "Token(token='$token')"
    }
}