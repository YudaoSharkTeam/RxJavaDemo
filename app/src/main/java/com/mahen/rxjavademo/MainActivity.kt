package com.mahen.rxjavademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.mahen.rxjavademo.viewmodel.TestViewModel
import io.reactivex.Observer
import org.reactivestreams.Subscriber

class MainActivity : BaseActivity() {
    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 初始化ViewModel
     * @return ViewModel
     */
    override fun initViewModel(): ViewModel {
        viewModel = getViewModel(TestViewModel::class.java)
        viewModel.lifecycleOwner = this
        return viewModel
    }

    fun testQuery(view: View) {
        viewModel.queryTest("123", "645", "5646")
    }

}