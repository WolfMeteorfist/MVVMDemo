package com.yuanshi.mvvmdemo.mainRequest.presenter;

import android.util.Log;

import com.yuanshi.mvvmdemo.mainRequest.model.IInfoModel;
import com.yuanshi.mvvmdemo.mainRequest.model.InfoModel;
import com.yuanshi.mvvmdemo.mainRequest.view.IMainView;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

/**
 * @author 大高玩
 */
public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenter";

    private IMainView mIMainView;
    private InfoModel mInfoModel;


    public MainPresenter(IMainView iMainView) {
        mIMainView = iMainView;
        mInfoModel = new InfoModel();
    }

    @Override
    public void requestData() {
        mIMainView.showRequestDialog();
        mInfoModel.requestData(mIMainView.getKey(), mIMainView.getDate(), new IInfoModel.OnRequestListener() {

            @Override
            public void onReceive(HistoryTodayModel response) {
                Log.e(TAG, "onReceive: " + response);

//                Collections.sort(response.getResult(), new Comparator<HistoryTodayModel.ResultBean>() {
//
//                    private Date mDate2;
//                    private Date mDate1;
//
//                    @Override
//                    public int compare(HistoryTodayModel.ResultBean o1, HistoryTodayModel.ResultBean o2) {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
//                        try {
//                            mDate1 = format.parse(o1.getDate());
//                            mDate2 = format.parse(o2.getDate());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                        return mDate2.compareTo(mDate1);
//                    }
//                });

                mIMainView.refreshData(response);
            }

            @Override
            public void onSucceed() {
                Log.e(TAG, "request data onSucceed: ");
                mIMainView.dismissDialog();
                mIMainView.refreshSucceed("获取列表成功");
            }

            @Override
            public void onFailed(String message) {
                mIMainView.dismissDialog();
                mIMainView.showAlertDialog(message);
            }
        });
    }

    @Override
    public void getShowDetail() {
        mInfoModel.requestDetail(mIMainView.getKey(),
                mIMainView.getFirstId(), new IInfoModel.OnRequestDetailListener() {
                    @Override
                    public void onDetailReceive(HistoryTodayDetailModel detailModel) {
                        mIMainView.refreshDetail(detailModel);
                    }

                    @Override
                    public void onSucceed() {
                        Log.e(TAG, "onSucceed!! ");
                    }

                    @Override
                    public void onFailed(String e) {

                        Log.e(TAG, "onFailed: " + e);
                    }

                });
    }

    @Override
    public void clickForDetail(String eid) {
        mIMainView.showRequestDetailDialog();
        mInfoModel.requestDetail(mIMainView.getKey(),
                eid, new IInfoModel.OnRequestDetailListener() {
                    @Override
                    public void onDetailReceive(HistoryTodayDetailModel detailModel) {
                        mIMainView.requestDetailSucceed(detailModel);
                    }

                    @Override
                    public void onSucceed() {
                        mIMainView.dismissDialog();
                        Log.e(TAG, "onSucceed!! ");
                    }

                    @Override
                    public void onFailed(String e) {
                        mIMainView.dismissDialog();
                        Log.e(TAG, "onFailed: " + e);
                    }

                });
    }
}
