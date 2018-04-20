package com.yuanshi.mvvmdemo.mainRequest.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yuanshi.mvvmdemo.R;
import com.yuanshi.mvvmdemo.detailRequest.view.TodayDetailActivity;
import com.yuanshi.mvvmdemo.mainRequest.presenter.MainPresenter;
import com.yuanshi.mvvmdemo.utils.FinalString;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayDetailModel;
import com.yuanshi.mvvmdemo.viewModel.HistoryTodayModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.floating_btn)
    FloatingActionButton mFloatingBtn;
    @BindView(R.id.recycler_view_main)
    RecyclerView mRecyclerViewMain;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @BindView(R.id.abl_main)
    AppBarLayout mAblMain;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.image_main)
    SimpleDraweeView mImageMain;
    private MyAdapter mMyAdapter;
    private Calendar mCalendar;
    private String date;
    MainPresenter mPresenter = new MainPresenter(this);
    private MaterialDialog requestDialog;
    private List<HistoryTodayModel.ResultBean> mTodayDatas = new LinkedList<>();
    private Unbinder mBind;
    private boolean floatingBtnShow = true;
    private ObjectAnimator mAnimator;
    private MaterialDialog requestDetailDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mBind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarMain.setTitle("");
        mMyAdapter = new MyAdapter(this);
        mCollapsingToolbar.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.TextAppearance_AppCompat_Large);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewMain.setLayoutManager(layoutManager);
        mRecyclerViewMain.setAdapter(mMyAdapter);
        setSupportActionBar(mToolbarMain);
        mRecyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled dy: " + dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    //顶部
                    Log.e(TAG, "onScrolled: " + "顶部");
                } else if (!recyclerView.canScrollVertically(1)) {
                    //底部
                    Log.e(TAG, "onScrolled: " + "底部");
                } else if (dy < -20 && !floatingBtnShow) {
                    Log.e(TAG, "onScrolled: " + "上滑");
                    mAnimator = ObjectAnimator.ofFloat(mFloatingBtn, "translationY", 300, 0.0f);
                    mAnimator.addListener(getListener());
                    mAnimator.setInterpolator(new FastOutSlowInInterpolator());
                    mAnimator.start();
                    floatingBtnShow = true;

                } else if (dy > 20 && floatingBtnShow) {
                    Log.e(TAG, "onScrolled: " + "下滑");
                    mAnimator = ObjectAnimator.ofFloat(mFloatingBtn, "translationY", 0.0f, 300);
                    mAnimator.addListener(getListener());
                    mAnimator.setInterpolator(new FastOutSlowInInterpolator());
                    mAnimator.start();
                    floatingBtnShow = false;
                }


            }

            @NonNull
            private Animator.AnimatorListener getListener() {
                return new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
            }
        });


    }

    @OnClick({R.id.floating_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_btn:
                showDatePickDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshData(HistoryTodayModel model) {
        Log.e(TAG, "refreshData: " + model);
        mTodayDatas.clear();
        mTodayDatas.addAll(model.getResult());
        int month = mCalendar.get(Calendar.MONTH) + 1;
        mCollapsingToolbar.setTitle(month + "月" + mCalendar.get(Calendar.DAY_OF_MONTH) + "日");
        mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshDetail(HistoryTodayDetailModel detailModel) {
        mImageMain.setImageURI(detailModel.getResult().get(0).getPicUrl().get(0).getUrl());
    }

    @Override
    public void refreshSucceed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        mPresenter.getShowDetail();
    }


    @Override
    public void showRequestDialog() {
        requestDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content("loading")
                .cancelable(false)
                .show();
    }

    @Override
    public void showRequestDetailDialog() {
        requestDetailDialog = new MaterialDialog.Builder(this)
                .content("正在加载")
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override
    public void showAlertDialog(String s) {
        Drawable drawable = getResources().getDrawable(R.drawable.popfailure, null);
        new MaterialDialog.Builder(this)
                .title("获取失败")
                .content(s)
                .icon(drawable)
                .show();

    }

    @Override
    public void dismissDialog() {
        if (requestDialog != null && requestDialog.isShowing()) {
            requestDialog.dismiss();
            requestDialog = null;
        }

        if (requestDetailDialog != null && requestDetailDialog.isShowing()) {
            requestDetailDialog.dismiss();
            requestDetailDialog = null;
        }
    }

    @Override
    public void showDatePickDialog() {
        mCalendar = Calendar.getInstance(TimeZone.getDefault());
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                Date time = mCalendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("M/d", Locale.CHINA);
                date = format.format(time);

                mPresenter.requestData();
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void requestDetailSucceed(HistoryTodayDetailModel model) {
        Intent intent = new Intent(this, TodayDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", model);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public String getKey() {
        return FinalString.APPKEY;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getFirstId() {
        return mTodayDatas.get(0).getE_id();
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        private WeakReference<MainActivity> activity;

        MyAdapter(MainActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity.get()).inflate(R.layout.recycler_view_main_item, null, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
            holder.mTvDate.setText(activity.get().mTodayDatas.get(position).getDate());
            holder.mTvContent.setText(activity.get().mTodayDatas.get(position).getTitle());
            holder.mTvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.get().mPresenter.clickForDetail(activity.get().mTodayDatas.get(position).getE_id());
                }
            });
        }

        @Override
        public int getItemCount() {
            return activity.get().mTodayDatas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_date)
            TextView mTvDate;
            @BindView(R.id.tv_content)
            TextView mTvContent;

            MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
