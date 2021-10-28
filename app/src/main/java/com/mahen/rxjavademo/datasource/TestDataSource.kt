package com.mahen.rxjavademo.datasource

import com.mahen.rxjavademo.mvvm.retrofit.BaseRemoteDataSource
import com.mahen.rxjavademo.mvvm.viewmodel.BaseViewModel
import com.mahen.rxjavademo.mvvm.retrofit.RequestCallback
import com.mahen.rxjavademo.service.TestService


class TestDataSource(baseViewModel: BaseViewModel) : BaseRemoteDataSource(baseViewModel),
    ITestDataSource {
    override fun test(
        billno: String,
        detail_time: String,
        username: String,
        responseCallback: RequestCallback<String>?
    ) {
        execute(
            getService(TestService::class.java).confirmInspect(billno, detail_time, username),
            responseCallback
        )
    }
}