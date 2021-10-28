package com.mahen.rxjavademo.datasource

import com.mahen.rxjavademo.mvvm.retrofit.RequestCallback

interface ITestDataSource {
    fun test(
        billno: String,
        detail_time: String,
        username: String,
        responseCallback: RequestCallback<String>
    )
}