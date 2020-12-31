package com.zsp.storeapp.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zsp.storeapp.R
import com.zsp.storeapp.activity.WebActivity
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.SportsNewsEntity
import com.zsp.storeapp.glide.GlideLoadUtils
import com.zsp.storeapp.util.IsNullUtil
import kotlinx.android.synthetic.main.item_sport_news.view.*

/**
 * description:
 * author:created by Andy on 2020/12/30 0030 17:41
 * email:zsp872126510@gmail.com
 */
class SportsNewsAdapter(
    layoutResId: Int,
    data: List<SportsNewsEntity?>?
) : BaseQuickAdapter<SportsNewsEntity?, BaseViewHolder>(
    layoutResId,
    data as MutableList<SportsNewsEntity?>?
) {
    override fun convert(holder: BaseViewHolder, item: SportsNewsEntity?) {

        if (IsNullUtil.getInstance().isEmpty(item?.img)) {
            holder.getView<ImageView>(R.id.iv).visibility = View.GONE
        } else {
            holder.getView<ImageView>(R.id.iv).visibility = View.VISIBLE
            GlideLoadUtils.getInstance().glideLoad(
                holder.itemView.context, item?.img,
                holder.getView(R.id.iv), 0
            )
        }
        holder.setText(R.id.tv, item?.title)
        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,WebActivity::class.java)
            intent.putExtra("href",item?.href)
            holder.itemView.context.startActivity(intent)
        }
    }

}