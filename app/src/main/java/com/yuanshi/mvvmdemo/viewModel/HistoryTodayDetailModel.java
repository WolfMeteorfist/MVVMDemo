package com.yuanshi.mvvmdemo.viewModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryTodayDetailModel implements Parcelable {

    /**
     * reason : success
     * result : [{"e_id":"1","title":"罗马共和国开始使用儒略历","content":"  r\n","picNo":"5","picUrl":[{"pic_title":"儒略历","id":1,"url":"http://images.juheapi.com/history/1_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/1_2.jpg"},{"pic_title":"","id":3,"url":"http://images.juheapi.com/history/1_3.jpg"},{"pic_title":"公式中的符号含义如下：","id":4,"url":"http://images.juheapi.com/history/1_4.jpg"},{"pic_title":"","id":5,"url":"http://images.juheapi.com/history/1_5.jpg"}]}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private ArrayList<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * e_id : 1
         * title : 罗马共和国开始使用儒略历
         * content :     在2061年前的今天，前45年1月1日 (农历冬月十九)，罗马共和国开始使用儒略历。

         * picNo : 5
         * picUrl : [{"pic_title":"儒略历","id":1,"url":"http://images.juheapi.com/history/1_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/1_2.jpg"},{"pic_title":"","id":3,"url":"http://images.juheapi.com/history/1_3.jpg"},{"pic_title":"公式中的符号含义如下：","id":4,"url":"http://images.juheapi.com/history/1_4.jpg"},{"pic_title":"","id":5,"url":"http://images.juheapi.com/history/1_5.jpg"}]
         */

        private String e_id;
        private String title;
        private String content;
        private String picNo;
        private List<PicUrlBean> picUrl;

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicNo() {
            return picNo;
        }

        public void setPicNo(String picNo) {
            this.picNo = picNo;
        }

        public List<PicUrlBean> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<PicUrlBean> picUrl) {
            this.picUrl = picUrl;
        }

        public static class PicUrlBean implements Serializable{

            /**
             * pic_title : 儒略历
             * id : 1
             * url : http://images.juheapi.com/history/1_1.jpg
             */

            private String pic_title;
            private int id;
            private String url;

            public String getPic_title() {
                return pic_title;
            }

            public void setPic_title(String pic_title) {
                this.pic_title = pic_title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public HistoryTodayDetailModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reason);
        dest.writeInt(this.error_code);
        dest.writeList(this.result);
    }

    protected HistoryTodayDetailModel(Parcel in) {
        this.reason = in.readString();
        this.error_code = in.readInt();
        this.result = new ArrayList<ResultBean>();
        in.readList(this.result, ResultBean.class.getClassLoader());
    }

    public static final Creator<HistoryTodayDetailModel> CREATOR = new Creator<HistoryTodayDetailModel>() {
        @Override
        public HistoryTodayDetailModel createFromParcel(Parcel source) {
            return new HistoryTodayDetailModel(source);
        }

        @Override
        public HistoryTodayDetailModel[] newArray(int size) {
            return new HistoryTodayDetailModel[size];
        }
    };
}
