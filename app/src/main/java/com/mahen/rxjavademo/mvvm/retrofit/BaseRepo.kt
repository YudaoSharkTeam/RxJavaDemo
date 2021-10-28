package com.mahen.rxjavademo.mvvm.retrofit

/**
 * 作为任务的调度器(我们可以随时更改remoteDataSource的实现)
 * @param T
 * @property remoteDataSource T
 * @constructor
 */
open class BaseRepo<T>(var remoteDataSource: T) {
}