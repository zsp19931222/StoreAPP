package com.zsp.storeapp.entity

import com.zsp.storeapp.util.CommonConfig
import java.io.Serializable

data class ProductEntity(
    val categoryId: Int,
    val createTime: String,
    val detail: String,
    val id: Int,
    val mainImage: String,
    val name: String,
    val price: Double,
    val status: Int,
    val stock: Int,
    val quantity: Int,
    val subImages: String,
    val subtitle: String,
    val updateTime: String
) : CommonConfig(),Serializable {
    override fun toString(): String {
        return "ProductEntity(categoryId=$categoryId, createTime='$createTime', detail='$detail', id=$id, mainImage='$mainImage', name='$name', price=$price, status=$status, stock=$stock, subImages='$subImages', subtitle='$subtitle', updateTime='$updateTime')"
    }
}