package repo

import androidx.lifecycle.MutableLiveData
import com.mahen.rxjavademo.datasource.ITestDataSource
import com.mahen.rxjavademo.mvvm.retrofit.BaseRepo
import com.mahen.rxjavademo.mvvm.retrofit.RequestCallback

class TestRepo(remoteDataSource: ITestDataSource) : BaseRepo<ITestDataSource>(remoteDataSource) {

    fun queryTest(
        billno: String,
        detail_time: String,
        username: String
    ): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        remoteDataSource.test(billno, detail_time, username, object : RequestCallback<String> {
            override fun onSuccess(result: String) {
                mutableLiveData.value = result
            }
        })
        return mutableLiveData
    }
}