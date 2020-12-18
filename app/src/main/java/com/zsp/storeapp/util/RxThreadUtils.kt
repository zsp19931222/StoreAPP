package com.zsp.storeapp.util

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.zsp.storeapp.net.DefaultObserver
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * description:
 * author:created by Andy on 2020/12/2 0002 09:37
 * email:zsp872126510@gmail.com
 */
object RxThreadUtils {

    fun <T> applyObservableAsync(): ObservableTransformer<T, T>? {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> bindToLifecycle(lifecycle: LifecycleProvider<T>): ObservableTransformer<T, T>? {
        return lifecycle.bindToLifecycle()
    }
}