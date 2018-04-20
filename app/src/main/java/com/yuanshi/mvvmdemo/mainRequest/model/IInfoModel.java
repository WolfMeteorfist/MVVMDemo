package com.yuanshi.mvvmdemo.mainRequest.model;

import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

public interface IInfoModel {

    void requestData(String key, String date, OnRequestListener listener);

    void requestDetail(String key, String id, OnRequestDetailListener listener);

    interface OnRequestDetailListener {

        void onDetailReceive(HistoryTodayDetailModel detailModel);

        void onSucceed();

        void onFailed(String e);

    }

    interface OnRequestListener {

        void onReceive(HistoryTodayModel response);

        void onSucceed();

        void onFailed(String message);
    }

}
