package com.yuanshi.mvvmdemo.mainRequest.model;

import com.yuanshi.mvvmdemo.utils.FinalString;
import com.yuanshi.mvvmdemo.utils.Url;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoModel implements IInfoModel {

    private Retrofit mRetrofit;

    @Override
    public void requestData(String key, String date, final OnRequestListener listener) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Url.HISTORY_TODAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Map<String, Object> map = new HashMap<>(2);
        map.put(FinalString.KEY, key);
        map.put(FinalString.DATE, date);
        InfoRequest_Interface request = mRetrofit.create(InfoRequest_Interface.class);
        request.getToday(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<HistoryTodayModel>() {

                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(HistoryTodayModel historyTodayModel) {
                        if (historyTodayModel.getError_code() == 0) {
                            listener.onReceive(historyTodayModel);
                            listener.onSucceed();
                        } else if (historyTodayModel.getError_code() == 10012) {
                            listener.onFailed("超过每日可允许请求次数.");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e.getMessage());
                        mDisposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        mDisposable.dispose();
                    }
                });
    }

    @Override
    public void requestDetail(String key, String id, final OnRequestDetailListener listener) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Url.HISTORY_TODAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        InfoRequest_Interface request = mRetrofit.create(InfoRequest_Interface.class);

        final Map<String, Object> map = new HashMap<>(2);
        map.put("key", key);
        map.put("e_id", id);

        request.getDetails(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HistoryTodayDetailModel>() {
                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(HistoryTodayDetailModel detailModel) {
                        listener.onDetailReceive(detailModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e.getMessage());
                        mDisposable.dispose();
                        map.clear();
                    }

                    @Override
                    public void onComplete() {
                        listener.onSucceed();
                        mDisposable.dispose();
                        map.clear();
                    }
                });

    }
}
