package com.yuanshi.mvvmdemo.mainRequest.presenter;

public interface IMainPresenter {

    /**
     * 申请数据
     */
    void requestData();


    void getShowDetail();


    /**
     * 跳转用
     * @param eid 事件ID
     */
    void clickForDetail(String eid);


}
