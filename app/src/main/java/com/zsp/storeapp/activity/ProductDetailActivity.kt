package com.zsp.storeapp.activity

import android.os.Bundle
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityProductDetailBinding
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.view.NumberButton
import com.zsp.storeapp.vm.ProductDetailViewModel
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.utils.KLog

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 16:09
 * email:zsp872126510@gmail.com
 */
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_product_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val bd = intent.getBundleExtra("key")
        val data: ProductEntity = bd?.getSerializable("data") as ProductEntity
        viewModel.image.set(data.mainImage)
        viewModel.price.set("￥ ${data.price}")
        viewModel.title.set(data.subtitle)
        viewModel.desc.set(data.detail)
        binding.btnNumber.buyMax = data.stock
        viewModel.total.set(setTotal(data.price))
        viewModel.productId.set(data.id)

        viewModel.numberButton.set(binding.btnNumber)
        binding.btnNumber.setOnWarnListener(object : NumberButton.OnWarnListener {
            override fun onWarningForInventory(inventory: Int) {
            }

            override fun onWarningForBuyMax(max: Int) {
            }

            override fun onTextChanger(number: Int) {
                viewModel.total.set(setTotal(number * data.price))
            }

        })
    }

    private fun setTotal(total: Double): String {
        return "合计：￥$total"
    }
}