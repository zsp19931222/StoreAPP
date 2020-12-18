package com.zsp.storeapp.vm

import android.app.Application
import android.content.Intent
import androidx.databinding.ObservableField
import com.luck.picture.lib.tools.SPUtils
import com.zsp.storeapp.activity.LoginRegisterActivity
import com.zsp.storeapp.activity.ProductDetailActivity
import com.zsp.storeapp.bean.CarBean
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.net.DefaultObserver
import com.zsp.storeapp.net.IdeaApi
import com.zsp.storeapp.util.HasLoginUtil
import com.zsp.storeapp.util.IsNullUtil
import com.zsp.storeapp.util.RxThreadUtils
import com.zsp.storeapp.util.ToastUtil
import com.zsp.storeapp.view.NumberButton
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseModel
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 16:00
 * email:zsp872126510@gmail.com
 */
class ProductDetailViewModel(application: Application) : BaseViewModel<BaseModel>(application) {
    var image: ObservableField<String> = ObservableField("")
    var price: ObservableField<String> = ObservableField("")
    var title: ObservableField<String> = ObservableField("")
    var desc: ObservableField<String> = ObservableField("")
    var total: ObservableField<String> = ObservableField("合计：￥")
    var productId: ObservableField<Int> = ObservableField()
    var numberButton: ObservableField<NumberButton> = ObservableField()
    var car = CarBean()
    var addBindingCommand: BindingCommand<BindingAction> =
        BindingCommand<BindingAction>(BindingAction {
            if (!HasLoginUtil.instance.hasLogin()) {
                startActivity(LoginRegisterActivity::class.java)
            } else {
                car.userId = SPUtils.getInstance().getInt("userId")
                car.checked = 0
                car.productId = productId.get()
                car.quantity = numberButton.get()?.number
                IdeaApi.getApiService().addProduct(car)
                    .compose(RxThreadUtils.applyObservableAsync()).subscribe(
                        object : DefaultObserver<BaseEntity<Any>>() {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onSuccess(response: BaseEntity<Any>?) {
                                ToastUtil.normalToast(getApplication(),"添加成功");
                            }

                        }
                    )
            }
        })
}