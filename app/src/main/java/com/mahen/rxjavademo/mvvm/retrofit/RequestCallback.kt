package com.mahen.rxjavademo.mvvm.retrofit

import com.mahen.rxjavademo.mvvm.exception.BaseException

interface RequestCallback<T> {
    fun onSuccess(t: T)
}

interface RequestMultiplyCallback<T> : RequestCallback<T> {
    fun onFail(e: BaseException?)
}