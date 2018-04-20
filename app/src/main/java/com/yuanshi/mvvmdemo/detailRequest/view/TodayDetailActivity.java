package com.yuanshi.mvvmdemo.detailRequest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuanshi.mvvmdemo.R;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 大神愁
 */
public class TodayDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_detail)
    ViewPager mViewpagerDetail;
    @BindView(R.id.back_detail)
    ImageView mBackDetail;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.content_detail)
    TextView mContentDetail;
    private List<String> imageList;
    private List<String> imageTitle;
    private List<View> views;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        initViewAndData();

    }

    private void initViewAndData() {
        Intent intent = getIntent();
        HistoryTodayDetailModel detailModel = intent.getExtras().getParcelable("detail");
        mToolbarMain.setTitle("");
        mContentDetail.setText(detailModel.getResult().get(0).getContent());
        imageList = new ArrayList<>();
        imageTitle = new ArrayList<>();
        views = new ArrayList<>();
        for (int i = 0; i < Integer.valueOf(detailModel.getResult().get(0).getPicNo()); i++) {
            imageList.add(detailModel.getResult().get(0).getPicUrl().get(i).getUrl());
            imageTitle.add(detailModel.getResult().get(0).getPicUrl().get(i).getPic_title());

            View view = LayoutInflater.from(this).inflate(R.layout.item_viewpager_detail, null);
            SimpleDraweeView image = view.findViewById(R.id.image_detail);
            TextView title = view.findViewById(R.id.image_title);
            image.setImageURI(imageList.get(i));
            title.setText(imageTitle.get(i));
            views.add(view);
        }


        mViewpagerDetail.setAdapter(new MyAdapter(this));

    }

    @OnClick({R.id.back_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_detail:
                finish();
                break;
            default:
                break;
        }
    }


    static class MyAdapter extends PagerAdapter {

        WeakReference<TodayDetailActivity> mWeakReference;
        TodayDetailActivity activity;

        MyAdapter(TodayDetailActivity activity) {
            mWeakReference = new WeakReference<>(activity);
            this.activity = mWeakReference.get();
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(activity.views.get(position));
            return activity.views.get(position);
        }

        @Override
        public int getCount() {
            return activity.imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(activity.views.get(position));
        }


        class MyHolder extends RecyclerView.ViewHolder {


            @BindView(R.id.image_detail)
            SimpleDraweeView mSimpleDraweeView;
            @BindView(R.id.image_title)
            TextView mTextView;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


    }

}
