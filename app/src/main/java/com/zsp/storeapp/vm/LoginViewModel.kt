package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableField
import com.luck.picture.lib.tools.SPUtils
import com.zsp.storeapp.bean.UserBean
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.entity.StoreUserEntity
import com.zsp.storeapp.entity.TokenEntity
import com.zsp.storeapp.net.DefaultObserver
import com.zsp.storeapp.net.IdeaApi
import com.zsp.storeapp.util.MD5Util
import com.zsp.storeapp.util.RxThreadUtils
import com.zsp.storeapp.util.ToastUtil
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseModel
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.KLog

/**
 * description:
 * author:created by Andy on 2020/11/26 0026 16:55
 * email:zsp872126510@gmail.com
 */
class LoginViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    var userName = ObservableField("zsp")
    var password = ObservableField("123")
    private val user = UserBean()

    var loginBinding: BindingCommand<BindingAction> = BindingCommand(BindingAction {
        user.username = userName.get()
        user.password = MD5Util.MD5(password.get())
        IdeaApi
            .getApiService()
            .getToken(user)
            .compose(RxThreadUtils.applyObservableAsync())
            .subscribe(object : DefaultObserver<BaseEntity<String>>() {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(response: BaseEntity<String>?) {
                    SPUtils.getInstance().put("token", response?.result)
                    getUserInfo()
                }

            })
    })

    fun getUserInfo() {
        IdeaApi
            .getApiService()
            .getUserInfo(user)
            .compose(RxThreadUtils.applyObservableAsync())
            .subscribe(object :
                io.reactivex.observers.DefaultObserver<BaseEntity<StoreUserEntity>>() {
                override fun onNext(t: BaseEntity<StoreUserEntity>) {
                    SPUtils.getInstance().put("userId", t.result.id)
                    ToastUtil.normalToast(getApplication(), "登录成功")
                    finish()
                }

                override fun onError(e: Throwable) {
                    KLog.d(e)
                }

                override fun onComplete() {
                }

            })
    }
}