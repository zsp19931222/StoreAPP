package com.zsp.storeapp.util

import com.google.gson.Gson

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 16:12
 * email:zsp872126510@gmail.com
 */
open class CommonConfig {
    companion object {
        fun <T> fromJson(json: String, clazz: Class<T>): T {
            return Gson().fromJson(json, clazz)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}