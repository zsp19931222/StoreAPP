package com.zsp.storeapp.push

import android.content.Context

/**
 * description:
 * author:created by Andy on 2019/7/2 0002 15:39
 * email:zsp872126510@gmail.com
 */
interface ISetAlias {
    fun setAlias(alias: String?)
    fun deleteAlias()
    fun initJPush(context: Context?, setDebugMode: Boolean)
    fun deleteAliasNet()
}