package com.zsp.storeapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zsp.storeapp.R
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.glide.GlideLoadUtils
import com.zsp.storeapp.util.IsNullUtil
import com.zsp.storeapp.view.IconTextView
import com.zsp.storeapp.view.NumberButton

/**
 * description:
 * author:created by Andy on 2020/12/9 0009 16:52
 * email:zsp872126510@gmail.com
 */
class CartAdapter(layoutResId: Int, data: List<ProductEntity?>?) :
    BaseQuickAdapter<ProductEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProductEntity?) {
        GlideLoadUtils.getInstance().glideLoad(
            helper?.itemView?.context,
            item?.mainImage,
            helper?.getView(R.id.iv_image),
            0
        )
        helper?.setText(R.id.tv_title, item?.subtitle?:"")
        helper?.setText(R.id.tv_price, "￥ ${item?.price?:0}")
        val btn=helper?.getView<NumberButton>(R.id.btn_number)
        btn?.buyMax=item?.stock?:0
        btn?.setCurrentNumber(item?.quantity?:1)
        val iconTextView=helper?.getView<IconTextView>(R.id.it_select)
        iconTextView?.isSelected=true
    }
}