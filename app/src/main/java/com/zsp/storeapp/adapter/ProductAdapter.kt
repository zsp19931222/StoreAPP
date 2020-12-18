package com.zsp.storeapp.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zsp.storeapp.R
import com.zsp.storeapp.activity.ProductDetailActivity
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.glide.GlideLoadUtils
import me.goldze.mvvmhabit.utils.KLog

/**
 * description:
 * author:created by Andy on 2020/12/2 0002 14:55
 * email:zsp872126510@gmail.com
 */
class ProductAdapter(layoutResId: Int, data: List<ProductEntity?>?) :
    BaseQuickAdapter<ProductEntity?, BaseViewHolder?>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProductEntity?) {
        GlideLoadUtils.getInstance().glideLoad(
            helper?.itemView?.context,
            item?.mainImage,
            helper?.getView(R.id.iv_image),
            0
        )
        helper?.setText(R.id.tv_title, item?.name)
        helper?.setText(R.id.tv_stock, "库存 ${item?.stock}")
        helper?.setText(R.id.tv_price, "￥ ${item?.price}")
        helper?.itemView?.setOnClickListener {
            KLog.d(item.toString())
            KLog.d(item?.toJson())
            val intent = Intent(it.context,ProductDetailActivity::class.java)
            val bd= Bundle()
            bd.putSerializable("data",item)
            intent.putExtra("key", bd)
            it.context.startActivity(intent)
        }
    }
}