package com.mahen.rxjavademo

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.mahen.rxjavademo.mvvm.BaseActionEvent

import com.mahen.rxjavademo.mvvm.viewmodel.IViewModelAction
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE


abstract class BaseActivity : AppCompatActivity() {
    //初始化加载框
    private val loadingDialog: ZLoadingDialog = ZLoadingDialog(this).apply {
        setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE) //设置类型
        setLoadingColor(Color.WHITE) //颜色
        setHintTextSize(16f) // 设置字体大小 dp
        setCanceledOnTouchOutside(false)
        setHintTextColor(Color.WHITE) // 设置字体颜色
        setDialogBackgroundColor(Color.parseColor("#00000000")) // 设置背景色，默认白色
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelEvent()
    }

    /**
     * 在此处初始化你的ViewModel
     * @return List<ViewModel?>?
     */
    protected open fun initViewModelList(): List<ViewModel>? {
        return null
    }

    /**
     * 在此处初始化你的ViewModel
     * @return List<ViewModel?>?
     */
    protected abstract fun initViewModel(): ViewModel?

    /**
     * 初始化ViewModel事件
     */
    private fun initViewModelEvent() {
        val viewModelList = initViewModelList()
        //如果实现了initViewModelList 则 initViewModel无效
        //TODO 这里可以使用注解的方式统一处理
        if (viewModelList != null && viewModelList.isNotEmpty()) {
            observeEvent(viewModelList)
        } else {
            val viewModel = initViewModel()
            if (viewModel != null) {
                val modelList: MutableList<ViewModel> = ArrayList()
                modelList.add(viewModel)
                observeEvent(modelList)
            }
        }
    }

    /**
     *
     * 最后把所以有关的ViewModel拿到此处 进行事件监听
     * @param viewModelList List<ViewModel>
     */
    private fun observeEvent(viewModelList: List<ViewModel>) {
        for (viewModel in viewModelList) {
            if (viewModel is IViewModelAction) {
                //让我们的BaseActivity 对 BaseViewModel的actionLiveData进行监听
                // 数据发生改变的时候进行视图的变化
                viewModel.actionLiveData!!.observe(this,
                    { baseActionEvent: BaseActionEvent? ->
                        if (baseActionEvent != null) {
                            when (baseActionEvent.action) {
                                BaseActionEvent.SHOW_LOADING_DIALOG -> {
                                    startLoading(baseActionEvent.message)
                                }
                                BaseActionEvent.DISMISS_LOADING_DIALOG -> {
                                    dismissLoading()
                                }
                                BaseActionEvent.SHOW_TOAST -> {
                                    showToast(baseActionEvent.message)
                                }
                                BaseActionEvent.FINISH -> {
                                    finish()
                                }
                                BaseActionEvent.FINISH_WITH_RESULT_OK -> {
                                    setResult(RESULT_OK)
                                    finish()
                                }
                            }
                        }
                    })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

    protected open fun startLoading() {
        startLoading(null)
    }

    protected open fun startLoading(message: String?) {
        loadingDialog.apply {
            setHintText(message)
            show()
        }
    }

    protected open fun dismissLoading() {
        loadingDialog.dismiss()
    }

    protected open fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected open fun finishWithResultOk() {
        setResult(RESULT_OK)
        finish()
    }

    /**
     * 获得ViewModel 改方法只能调用一次
     * @param clazz 获得的ViewModel类
     */
    fun <T : ViewModel> getViewModel(clazz: Class<T>): T {
        val viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                clazz
            )
        return viewModel
    }
}