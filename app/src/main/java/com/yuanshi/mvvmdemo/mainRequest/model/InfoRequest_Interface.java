package com.yuanshi.mvvmdemo.mainRequest.model;

import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InfoRequest_Interface {

    // HISTORY_TODAY_BASE_URL = "http://v.juhe.cn/todayOnhistory/";

    /**
     * 注解
     */
    @FormUrlEncoded
    @POST("queryEvent.php")
    Observable<HistoryTodayModel> getToday(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("queryDetail.php")
    Observable<HistoryTodayDetailModel> getDetails(@FieldMap Map<String, Object> map);

//    @POST("queryEvent.php")
//    Call<historyTodayDetailModel> getToday(@Field("key") String key, @Field("date") String date);

}
