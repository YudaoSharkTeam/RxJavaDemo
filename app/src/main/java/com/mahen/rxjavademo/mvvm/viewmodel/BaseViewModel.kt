package com.mahen.rxjavademo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahen.rxjavademo.mvvm.BaseActionEvent
import androidx.lifecycle.LifecycleOwner


open class BaseViewModel : ViewModel(), IViewModelAction {
    var lifecycleOwner: LifecycleOwner? = null
    override var actionLiveData: MutableLiveData<BaseActionEvent>? = null

    init {
        actionLiveData = MutableLiveData()
    }

    override fun startLoading() {
        startLoading(null);
    }

    override fun startLoading(message: String?) {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.SHOW_LOADING_DIALOG)
        baseActionEvent.message = message
        actionLiveData!!.value = baseActionEvent
    }

    override fun dismissLoading() {
        actionLiveData?.value = BaseActionEvent(BaseActionEvent.DISMISS_LOADING_DIALOG);
    }

    override fun showToast(message: String?) {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.SHOW_TOAST)
        baseActionEvent.message = message
        actionLiveData!!.value = baseActionEvent
    }

    override fun finish() {
        actionLiveData?.value = BaseActionEvent(BaseActionEvent.FINISH)
    }

    override fun finishWithResultOk() {
        actionLiveData?.value = BaseActionEvent(BaseActionEvent.FINISH_WITH_RESULT_OK)
    }

}