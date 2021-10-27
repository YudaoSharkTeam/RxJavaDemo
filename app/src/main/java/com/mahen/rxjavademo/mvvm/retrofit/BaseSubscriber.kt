package com.mahen.rxjavademo.mvvm.retrofit

import android.util.Log
import com.mahen.rxjavademo.mvvm.viewmodel.BaseViewModel
import io.reactivex.observers.DisposableObserver
import android.widget.Toast

import com.mahen.rxjavademo.mvvm.retrofit.config.HttpCode

import com.mahen.rxjavademo.mvvm.exception.BaseException


/**
 * 订阅者 或者叫 监听者
 * @param T
 */
class BaseSubscriber<T>(
    private val baseViewModel: BaseViewModel,
    var requestCallback: RequestCallback<T>? = null
) : DisposableObserver<T>() {

    //成功执行设置的回调
    override fun onNext(t: T) {
        requestCallback?.onSuccess(t)
    }

    /**
     * 这里如果设置的回调为 RequestMultiplyCallback类型就执行 onFail的逻辑
     * @param e Throwable
     */
    override fun onError(e: Throwable) {
        Log.i("BaseSubscriber", "onError: ", e)

        if (requestCallback is RequestMultiplyCallback) {
            val callback = requestCallback as RequestMultiplyCallback
            if (e is BaseException) {
                callback.onFail(e)
            } else {
                callback.onFail(BaseException(HttpCode.CODE_UNKNOWN, e.message))
            }
        } else {
            //这里最终会调用到 Activity 的showToast
            baseViewModel.showToast(e.message)
        }
    }

    override fun onComplete() {
    }
}