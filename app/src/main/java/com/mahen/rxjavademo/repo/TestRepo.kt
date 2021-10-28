package com.mahen.rxjavademo.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mahen.rxjavademo.datasource.ITestDataSource
import com.mahen.rxjavademo.mvvm.exception.BaseException
import com.mahen.rxjavademo.mvvm.retrofit.BaseRepo
import com.mahen.rxjavademo.mvvm.retrofit.RequestCallback
import com.mahen.rxjavademo.mvvm.retrofit.RequestMultiplyCallback

class TestRepo(remoteDataSource: ITestDataSource) : BaseRepo<ITestDataSource>(remoteDataSource) {

    /**
     * 数据返回LiveData 外面可以监听这个LiveData的变化
     * @param billno String
     * @param detail_time String
     * @param username String
     * @return MutableLiveData<String>
     */
    fun queryTest(
        billno: String,
        detail_time: String,
        username: String
    ): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        remoteDataSource.test(billno, detail_time, username, object :
            RequestMultiplyCallback<String> {
            override fun onSuccess(result: String) {
                mutableLiveData.value = result
            }

            override fun onFail(e: BaseException?) {
                Log.i("SharkChilli", "onFail: eeeeeeeee")
            }
        })
        return mutableLiveData
    }
}