package com.zsp.storeapp.entity

import java.security.Timestamp

/**
 * description:
 * author:created by Andy on 2020/12/4 0004 16:53
 * email:zsp872126510@gmail.com
 */
data class StoreUserEntity(
    val answer: Any,
    val createTime: String,
    val email: String,
    val id: Int,
    val password: String,
    val phone: Any,
    val photo: Any,
    val question: Any,
    val role: Int,
    val updateTime: String,
    val username: String
    ) {
    override fun toString(): String {
        return "StoreUserEntity(id=$id, username=$username, password=$password, email=$email, phone=$phone, question=$question, answer=$answer, role=$role, createTime=$createTime, updateTime=$updateTime, photo=$photo)"
    }
}
