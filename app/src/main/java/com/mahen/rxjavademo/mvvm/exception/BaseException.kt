package com.mahen.rxjavademo.mvvm.exception

import com.mahen.rxjavademo.mvvm.retrofit.config.HttpCode
import java.lang.RuntimeException

open class BaseException : RuntimeException {
    //默认为位置错误
    var errorCode: String = HttpCode.CODE_UNKNOWN
        private set

    constructor() {}
    constructor(errorCode: String, errorMessage: String?) : super(errorMessage) {
        this.errorCode = errorCode
    }
}