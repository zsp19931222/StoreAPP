package com.zsp.storeapp.bean

/**
 * description:
 * author:created by Andy on 2020/12/9 0009 15:59
 * email:zsp872126510@gmail.com
 */
class CarBean(
    var userId: Int = 0,
    var productId: Int? = null,
    var quantity: Int? = null,
    var checked: Int? = null,
) {

    override fun toString(): String {
        return "CarBean(userId=$userId, productId=$productId, quantity=$quantity, checked=$checked)"
    }
}