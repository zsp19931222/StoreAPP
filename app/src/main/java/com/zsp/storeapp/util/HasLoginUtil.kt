package com.zsp.storeapp.util

import com.luck.picture.lib.tools.SPUtils

/**
 * description:
 * author:created by Andy on 2020/12/9 0009 16:16
 * email:zsp872126510@gmail.com
 */
class HasLoginUtil private constructor() {
    companion object {
        val instance = HasLoginUtilHolder.holder
    }

    private object HasLoginUtilHolder {
        val holder= HasLoginUtil()
    }

    fun hasLogin():Boolean{
        return !(IsNullUtil.getInstance().isEmpty(SPUtils.getInstance().getString("token"))||IsNullUtil.getInstance().isEmpty(SPUtils.getInstance().getInt("userId")))
    }
}