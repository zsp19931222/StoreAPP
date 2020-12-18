package com.zsp.storeapp.entity

data class CategoryEntity(
    val createTime: Any,
    val id: Int,
    val name: String,
    val parentId: Any,
    val sortOrder: Any,
    val status: Int,
    val updateTime: Any
) {
    override fun toString(): String {
        return "CategoryEntity(createTime=$createTime, id=$id, name='$name', parentId=$parentId, sortOrder=$sortOrder, status=$status, updateTime=$updateTime)"
    }
}