package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.ImageAdapter
import com.zsp.storeapp.adapter.ProductAdapter
import com.zsp.storeapp.entity.BannerResult
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.entity.ListResultEntity
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.net.DefaultObserver
import com.zsp.storeapp.net.IdeaApi
import com.zsp.storeapp.util.RxThreadUtils
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseModel
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand


/**
 * description:
 * author:created by Andy on 2020/12/1 0001 14:24
 * email:zsp872126510@gmail.com
 */
class HomeViewModel(application: Application) : BaseViewModel<BaseModel?>(application) {
    var bannerResults: ObservableList<BannerResult> = ObservableArrayList()
    var imageAdapter = ImageAdapter(bannerResults)

    var productList: ObservableList<ProductEntity> = ObservableArrayList()
    var productAdapter: ProductAdapter = ProductAdapter(R.layout.item_soft_right, productList)

    var pageSize = 20
    var pageNo = 1

    var bindingCommand: BindingCommand<BindingAction>? =
        BindingCommand<BindingAction>(BindingAction {
        })

    /**
     * description: 获取banner
     * author: Andy
     * date: 2020/12/1 0001 16:32
     */
    fun getBannerList() = IdeaApi.getApiService()
        .banner
        .compose(RxThreadUtils.applyObservableAsync())
        .subscribe(object : DefaultObserver<BaseEntity<ListResultEntity<BannerResult>>>() {
            override fun onSuccess(response: BaseEntity<ListResultEntity<BannerResult>>) {
                bannerResults.addAll(response.result)
                imageAdapter.notifyDataSetChanged()
            }

            override fun onSubscribe(d: Disposable) {
            }
        })

    /**
     * description:
     * author: Andy
     * date: 2020/12/2 0002 15:08
     */
    fun getProductList() = IdeaApi
        .getApiService()
        .getProductList(pageSize, pageNo)
        .compose(RxThreadUtils.applyObservableAsync())
        .subscribe(object : DefaultObserver<BaseEntity<ListResultEntity<ProductEntity>>>() {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(response: BaseEntity<ListResultEntity<ProductEntity>>?) {
                productList.addAll(response?.result ?: productList)
                productAdapter.notifyDataSetChanged()
            }

        })

}