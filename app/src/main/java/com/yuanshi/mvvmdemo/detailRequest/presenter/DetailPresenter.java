package com.yuanshi.mvvmdemo.detailRequest.presenter;

import com.yuanshi.mvvmdemo.detailRequest.model.DetailModel;
import com.yuanshi.mvvmdemo.detailRequest.view.ITodayDetailView;
import com.yuanshi.mvvmdemo.mainRequest.view.IMainView;

public class DetailPresenter implements IDetailPresenter {

    private static final String TAG = "DetailRequestPresenter";

    ITodayDetailView mITodayDetailView;
    IMainView mIMainView;
    DetailModel mDetailModel;

    public DetailPresenter(ITodayDetailView iTodayDetailView) {
        mITodayDetailView = iTodayDetailView;
        mDetailModel = new DetailModel();
    }

    public DetailPresenter(IMainView iMainView) {
        mIMainView = iMainView;
        mDetailModel = new DetailModel();
    }

    @Override
    public void requestDetail() {
//        mDetailModel.requestDetail(mIMainView == null ? mITodayDetailView.getAppKey() : mIMainView.getKey(),
//                mIMainView == null ? mITodayDetailView.getEid() : mIMainView.getFirstId(), new IDetailModel.OnRequestListener() {
//                    @Override
//                    public void requestReceive(HistoryTodayDetailModel detailModel) {
//                        if (mIMainView != null) {
//                            mIMainView.refreshDetail(detailModel);
//                        } else {
//                            mITodayDetailView.refreshDate(detailModel);
//                        }
//                    }
//
//                    @Override
//                    public void requestSucceed() {
//                        Log.e(TAG, "onSucceed!! ");
//                    }
//
//                    @Override
//                    public void requestFailed(String e) {
//                        Log.e(TAG, "onFailed: " + e);
//                    }
//                });
    }
}
