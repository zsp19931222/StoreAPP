package com.zsp.storeapp.entity

data class BaseEntity<T>(
    val code: String,
    val message: String,
    val result: T
) {
    override fun toString(): String {
        return "BaseEntity(code='$code', message='$message', result=$result)"
    }
}