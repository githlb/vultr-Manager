package com.zaozao.vultrManager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.zaozao.vultrManager.R;
import com.zaozao.vultrManager.http.HttpApi;
import com.zaozao.vultrManager.utils.AppUtil;
import com.zaozao.vultrManager.utils.ErrorCode;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by sean on 2016/09/30.
 */
public class ControlFragment extends Fragment {
    @InjectView(R.id.btn_start)
    TextView mBtnStart;
    @InjectView(R.id.btn_stop)
    TextView mBtnStop;
    @InjectView(R.id.btn_reboot)
    TextView mBtnReboot;
    @InjectView(R.id.loading_view)
    View mLoadingView;
    @InjectView(R.id.loading_img_load)
    View mLoadingImg;
    @InjectView(R.id.loading_tv_hint)
    TextView mLoadingHint;

    private String mSubId = null;
    private int mLastRequest = -1;
    private static final int TYPE_START = 0;
    private static final int TYPE_STOP = 1;
    private static final int TYPE_RESTART = 2;

    private TextHttpResponseHandler mStartResponseHandler = null;
    private TextHttpResponseHandler mStopResponseHandler = null;
    private TextHttpResponseHandler mRestartResponseHandler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private String getStrByType(int type) {
        String str = null;
        switch (type) {
            case TYPE_START:
                str = "启动";
                break;
            case TYPE_STOP:
                str = "停止";
                break;
            case TYPE_RESTART:
                str = "重启";
                break;
        }
        return str;
    }

    private void showNoticeByType() {
        String str = getHintByType();
        if (!TextUtils.isEmpty(str)) {
            AppUtil.showToast(getActivity(), str);
        }
    }

    private String getHintByType() {
        String str = null;
        switch (mLastRequest) {
            case TYPE_RESTART:
                str = "正在执行重启请求，请稍后";
                break;
            case TYPE_START:
                str = "正在执行启动请求，请稍后";
                break;
            case TYPE_STOP:
                str = "正在执行停止请求，请稍后";
                break;
        }
        return str;
    }

    private void initView() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastRequest >= 0) {
                    showNoticeByType();
                    return;
                }
                switch (v.getId()) {
                    case R.id.btn_start:
                        mLastRequest = TYPE_START;
                        if (null == mStartResponseHandler) {
                            mStartResponseHandler = getResponseHandler(TYPE_START);
                        }
                        HttpApi.startServer(getActivity(), mSubId, mStartResponseHandler);
                        break;
                    case R.id.btn_stop:
                        mLastRequest = TYPE_STOP;
                        if (null == mStopResponseHandler) {
                            mStopResponseHandler = getResponseHandler(TYPE_STOP);
                        }
                        HttpApi.stopServer(getActivity(), mSubId, mStopResponseHandler);
                        break;
                    case R.id.btn_reboot:
                        mLastRequest = TYPE_RESTART;
                        if (null == mRestartResponseHandler) {
                            mRestartResponseHandler = getResponseHandler(TYPE_RESTART);
                        }
                        HttpApi.rebootServer(getActivity(), mSubId, mRestartResponseHandler);
                        break;
                }
            }
        };
        mBtnStart.setOnClickListener(onClickListener);
        mBtnStop.setOnClickListener(onClickListener);
        mBtnReboot.setOnClickListener(onClickListener);
    }

    private TextHttpResponseHandler getResponseHandler(final int type) {
        return new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                onBeforeExcute();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppUtil.showToast(getActivity(), ErrorCode.getMsgByCode(ErrorCode.CODE_CONNECT_FAIL));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (200 != statusCode) {
                    AppUtil.showToast(getActivity(), ErrorCode.getMsgByCode(statusCode));
                } else {
                    AppUtil.showToast(getActivity(), getStrByType(type) + "成功");
                }
            }

            @Override
            public void onFinish() {
                onAfterExcute();
                mLastRequest = -1;
            }
        };
    }

    private void onBeforeExcute() {
        HttpApi.setTimeout(100 * 1000);//超时时间太小会请求失败
        showLoading(true);
    }

    private void onAfterExcute() {
        HttpApi.setTimeout(10 * 1000);//恢复默认超时时间
        showLoading(false);
    }

    private void showLoading(boolean isShow) {
        if (isShow) {
            RotateAnimation rotateAnimation = new RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(500);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setRepeatCount(-1);
            mLoadingHint.setText(getHintByType());
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingImg.setAnimation(rotateAnimation);
        } else {
            mLoadingView.setVisibility(View.INVISIBLE);
            mLoadingImg.setAnimation(null);
        }
    }

    public void setSubId(String id) {
        mSubId = id;
    }
}
