package com.mahen.rxjavademo.viewmodel

import com.mahen.rxjavademo.mvvm.viewmodel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import com.mahen.rxjavademo.datasource.TestDataSource
import com.mahen.rxjavademo.repo.TestRepo


class TestViewModel : BaseViewModel() {
    var testLiveData: MutableLiveData<String>? = null

    var testRepo: TestRepo? = null

    init {
        testLiveData = MutableLiveData()
        testRepo = TestRepo(TestDataSource(this))
    }

    fun queryTest(
        billno: String,
        detail_time: String,
        username: String
    ) {
        testRepo?.queryTest(billno, detail_time, username)?.observe(lifecycleOwner!!) {
            testLiveData?.value = it
        }
    }
}