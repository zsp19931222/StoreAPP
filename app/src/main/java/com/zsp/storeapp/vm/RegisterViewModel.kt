package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableField
import com.luck.picture.lib.tools.SPUtils
import com.zsp.storeapp.bean.UserBean
import com.zsp.storeapp.entity.BaseEntity
import com.zsp.storeapp.entity.StoreUserEntity
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

/**
 * description:
 * author:created by Andy on 2020/12/4 0004 15:30
 * email:zsp872126510@gmail.com
 */
class RegisterViewModel(application: Application) :BaseViewModel<BaseModel>(application) {
    var userName= ObservableField("")
    var password= ObservableField("")
    var email= ObservableField("")

    var registerBinding: BindingCommand<BindingAction> =
        BindingCommand<BindingAction>(BindingAction {
            SPUtils.getInstance().put("token","")
            var user=UserBean()
            user.password=MD5Util.MD5(password.get())
            user.username=userName.get()
            user.email=email.get()
            IdeaApi.getApiService().register(user).compose(RxThreadUtils.applyObservableAsync()).subscribe(object : DefaultObserver<BaseEntity<Any>>(){
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(response:BaseEntity<Any>) {
                    ToastUtil.normalToast(application.applicationContext,"注册成功")
                }

            })
        })
}