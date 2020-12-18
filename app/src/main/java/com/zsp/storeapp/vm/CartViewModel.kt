package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.luck.picture.lib.tools.SPUtils
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.CartAdapter
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.entity.ListResultEntity
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.net.DefaultObserver
import com.zsp.storeapp.net.IdeaApi
import com.zsp.storeapp.util.RxThreadUtils
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseModel
import me.goldze.mvvmhabit.base.BaseViewModel

/**
 * description:
 * author:created by Andy on 2020/12/9 0009 15:29
 * email:zsp872126510@gmail.com
 */
class CartViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    var productList: ObservableList<ProductEntity> = ObservableArrayList()
    var adapter: CartAdapter = CartAdapter(R.layout.item_car, productList)

    fun getData() = IdeaApi
        .getApiService()
        .getCartList(SPUtils.getInstance().getInt("userId"))
        .compose(RxThreadUtils.applyObservableAsync())
        .subscribe(object : DefaultObserver<BaseEntity<ListResultEntity<ProductEntity>>>() {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onSuccess(response: BaseEntity<ListResultEntity<ProductEntity>>?) {
                productList.addAll(response?.result ?: productList)
                adapter.notifyDataSetChanged()
            }

        })
}