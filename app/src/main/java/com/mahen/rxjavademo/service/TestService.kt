package com.mahen.rxjavademo.service

import com.mahen.rxjavademo.mvvm.retrofit.model.RequestModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestService {
    //点检保养确认
    @FormUrlEncoded
    @POST("EquipmentCheckSure")
    fun confirmInspect(
        @Field("billno") billno: String,
        @Field("detail_time") detail_time: String,
        @Field("username") username: String
    ): Observable<RequestModel<String>>

}