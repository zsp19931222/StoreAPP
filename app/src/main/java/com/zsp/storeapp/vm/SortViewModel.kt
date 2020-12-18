package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.CategoryAdapter
import com.zsp.storeapp.adapter.ProductAdapter
import com.zsp.storeapp.entity.CategoryEntity
import com.zsp.storeapp.entity.ProductEntity
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.entity.ListResultEntity
import com.zsp.storeapp.net.DefaultObserver
import com.zsp.storeapp.net.IdeaApi
import com.zsp.storeapp.util.RxThreadUtils
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseModel
import me.goldze.mvvmhabit.base.BaseViewModel

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 15:19
 * email:zsp872126510@gmail.com
 */
class SortViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    var categoryList: ObservableList<CategoryEntity> = ObservableArrayList()
    var categoryAdapter: CategoryAdapter = CategoryAdapter(R.layout.item_soft_left, categoryList)

    var productList: ObservableList<ProductEntity> = ObservableArrayList()
    var productAdapter: ProductAdapter = ProductAdapter(R.layout.item_soft_right, productList)


    /**
     * @description:
     * @author: zsp
     * @date: 2020/12/3 0003 15:34
     * @param null
     * @return:
     */
    fun getCategoryList() = IdeaApi.getApiService().categoryList
        .compose(RxThreadUtils.applyObservableAsync())
        .subscribe(object : DefaultObserver<BaseEntity<ListResultEntity<CategoryEntity>>>() {
            override fun onSuccess(response: BaseEntity<ListResultEntity<CategoryEntity>>?) {
                categoryList.addAll(response?.result?:categoryList)
                categoryAdapter.notifyDataSetChanged()
                getProductList(categoryList[0].id)
            }

            override fun onSubscribe(d: Disposable) {
            }

        })

    fun getProductList(categoryId: Int) = IdeaApi.getApiService().getProductListById(categoryId)
        .compose(RxThreadUtils.applyObservableAsync())
        .subscribe(object : DefaultObserver<BaseEntity<ListResultEntity<ProductEntity>>>() {
            override fun onSuccess(response: BaseEntity<ListResultEntity<ProductEntity>>?) {
                productList.clear()
                productList.addAll(response?.result?:productList)
                productAdapter.notifyDataSetChanged()
            }

            override fun onSubscribe(d: Disposable) {
            }
        })


}