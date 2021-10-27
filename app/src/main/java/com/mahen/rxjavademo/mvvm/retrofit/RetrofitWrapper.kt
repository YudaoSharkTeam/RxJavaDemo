package com.mahen.rxjavademo.mvvm.retrofit

interface RetrofitWrapper<T> {
    fun initRetrofit(tClass: Class<T>)
    fun getService(): T
}