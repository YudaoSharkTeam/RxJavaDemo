package com.mahen.rxjavademo.mvvm.exception

import com.mahen.rxjavademo.mvvm.retrofit.config.HttpCode

class ServerResultException(errorCode: String, errorMessage: String?) :
    BaseException(errorCode, errorMessage) {
}