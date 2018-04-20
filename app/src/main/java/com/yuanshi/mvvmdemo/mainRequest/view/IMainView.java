package com.yuanshi.mvvmdemo.mainRequest.view;

import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

public interface IMainView {

    void refreshData(HistoryTodayModel model);

    void refreshDetail(HistoryTodayDetailModel detailModel);

    void refreshSucceed(String s);

    void showRequestDialog();

    void showRequestDetailDialog();

    void showAlertDialog(String s);

    void dismissDialog();

    void showDatePickDialog();

    void requestDetailSucceed(HistoryTodayDetailModel model);

    String getKey();

    String getDate();

    String getFirstId();


}
