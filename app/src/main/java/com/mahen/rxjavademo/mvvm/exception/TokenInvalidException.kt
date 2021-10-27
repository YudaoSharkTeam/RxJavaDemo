package com.mahen.rxjavademo.mvvm.exception

import com.mahen.rxjavademo.mvvm.retrofit.config.HttpCode

class TokenInvalidException: BaseException(HttpCode.CODE_TOKEN_INVALID, "Token失效") {
}