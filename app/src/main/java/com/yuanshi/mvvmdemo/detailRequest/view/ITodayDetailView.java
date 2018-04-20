package com.yuanshi.mvvmdemo.detailRequest.view;

import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;

public interface ITodayDetailView {

    String getEid();

    String getAppKey();

    void showDialog();

    void dismissDialog();

    void refreshDate(HistoryTodayDetailModel detailModel);



}
