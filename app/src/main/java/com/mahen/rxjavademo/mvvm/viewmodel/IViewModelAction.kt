package com.mahen.rxjavademo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mahen.rxjavademo.mvvm.BaseActionEvent


interface IViewModelAction {
    var actionLiveData: MutableLiveData<BaseActionEvent>?

    fun startLoading()

    fun startLoading(message: String?)

    fun dismissLoading()

    fun showToast(message: String?)

    fun finish()

    fun finishWithResultOk()

}