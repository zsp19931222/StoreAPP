package com.zsp.storeapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zsp.storeapp.R
import com.zsp.storeapp.entity.CategoryEntity

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 15:27
 * email:zsp872126510@gmail.com
 */
class CategoryAdapter(layoutResId: Int, data: List<CategoryEntity?>?) :
    BaseQuickAdapter<CategoryEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: CategoryEntity?) {
        helper?.setText(R.id.tv_name,item?.name)
    }
}