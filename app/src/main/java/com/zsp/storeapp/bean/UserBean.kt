package com.zsp.storeapp.bean

import java.security.Timestamp

/**
 * description:
 * author:created by Andy on 2020/12/7 0007 14:06
 * email:zsp872126510@gmail.com
 */
class UserBean (
    var id: Int = 0,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var question: String? = null,
    var answer: String? = null,
    var role: Int = 0,
    var createTime: Timestamp? = null,
    var updateTime: Timestamp? = null,
    var photo: String? = null,
    ) {
    override fun toString(): String {
        return "StoreUserEntity(id=$id, username=$username, password=$password, email=$email, phone=$phone, question=$question, answer=$answer, role=$role, createTime=$createTime, updateTime=$updateTime, photo=$photo)"
    }
}
