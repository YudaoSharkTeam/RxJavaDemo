package com.mahen.rxjavademo.mvvm.retrofit

import com.mahen.rxjavademo.mvvm.retrofit.model.RequestModel
import com.mahen.rxjavademo.mvvm.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * 远程数据源
 * @property baseViewModel BaseViewModel
 * @property compositeDisposable CompositeDisposable
 * @constructor
 */
abstract class BaseRemoteDataSource(private val baseViewModel: BaseViewModel) {
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun <T> getService(clz: Class<T>): T {
        return RetrofitManagement.getService(clz)
    }

    open fun <T> getService(clz: Class<T>, host: String): T {
        return RetrofitManagement.getService(clz, host)
    }

    open fun <T> applySchedulers(): ObservableTransformer<RequestModel<T>, T>? {
        return RetrofitManagement.applySchedulers()
    }

    protected open fun <T> execute(
        observable: Observable<RequestModel<T>>,
        callback: RequestCallback<T>?
    ) {
        execute(observable, BaseSubscriber(baseViewModel, callback), true)
    }

    protected open fun <T> execute(
        observable: Observable<RequestModel<T>>,
        callback: RequestMultiplyCallback<T>?
    ) {
        execute(observable, BaseSubscriber(baseViewModel, callback), true)
    }

    open fun <T> executeWithoutDismiss(
        observable: Observable<RequestModel<T>>,
        observer: Observer<T>
    ) {
        execute(observable, observer, false)
    }

    open fun <T> execute(
        observable: Observable<RequestModel<T>>,
        observer: Observer<T>,
        isDismiss: Boolean
    ) {
        addDisposable(
            observable
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .compose(if (isDismiss) loadingTransformer<T>() else loadingTransformerWithoutDismiss<T>())
                .subscribeWith(observer) as Disposable
        )
    }

    open fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    open fun startLoading() {
        baseViewModel.startLoading()
    }

    open fun dismissLoading() {
        baseViewModel.dismissLoading()
    }

    open fun <T> loadingTransformer(): ObservableTransformer<T, T>? {
        return ObservableTransformer { observable: Observable<T> ->
            observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable: Disposable? -> startLoading() }
                .doFinally { dismissLoading() }
        }
    }

    open fun <T> loadingTransformerWithoutDismiss(): ObservableTransformer<T, T>? {
        return ObservableTransformer { observable: Observable<T> ->
            observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable: Disposable? -> startLoading() }
        }
    }
}