package com.zsp.storeapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zsp.storeapp.entity.SportsNewsEntity

/**
 * description:
 * author:created by Andy on 2020/12/30 0030 17:43
 * email:zsp872126510@gmail.com
 */
internal class Adapter(
    layoutResId: Int,
    data: List<SportsNewsEntity?>?
) : BaseQuickAdapter<SportsNewsEntity?, BaseViewHolder>(layoutResId,
    data as MutableList<SportsNewsEntity?>?
) {
    override fun convert(holder: BaseViewHolder, item: SportsNewsEntity?) {
    }

}