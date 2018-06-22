package com.myxh.baby.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.VideoView;

import com.myxh.baby.R;
import com.myxh.baby.network.HttpListener;
import com.myxh.baby.ui.base.BaseFragment;
import com.yolanda.nohttp.rest.Response;

public class ViewFragment extends BaseFragment implements HttpListener<String> ,View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private VideoView videoView;
    private static final int FLING_MIN_DISTANCE = 50;   //最小距离
    private static final int FLING_MIN_VELOCITY = 0;  //最小速度
    static int page = 1;
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look, null);
        initPopMenu();
        webView = (WebView)view.findViewById(R.id.webgif);
        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setInitialScale(100);
        webView.loadUrl("file:///android_asset/gif_1.html");
        mGestureDetector = new GestureDetector(getActivity(), myGestureListener);
        WebView mLl = (WebView) view.findViewById(R.id.webgif);//布局的主容器
        mLl.setOnTouchListener(this);//将主容器的监听交给本activity，本activity再交给mGestureDetector
        mLl.setLongClickable(true);   //必需设置这为true 否则也监听不到手势
        return view;
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }

    private void initPopMenu() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_x, null);

    }

    public void onClick(View view) {

    }

    GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e("<--滑动测试-->", "开始滑动");
            float x = e1.getX()-e2.getX();
            float x2 = e2.getX()-e1.getX();
            if(x>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
                //Toast.makeText(getActivity(), "向左手势", Toast.LENGTH_SHORT).show();
                if(page < 3){
                    page +=1;
                }else if (page == 3){
                    page =1;
                }
                getPage();

            }else if(x2>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
                //Toast.makeText(getActivity(), "向右手势", Toast.LENGTH_SHORT).show();
                if(page > 1){
                    page -=1;
                }else if (page == 1){
                    page =3;
                }
                getPage();
            }

            return false;
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return  mGestureDetector.onTouchEvent(motionEvent);
    }

    void getPage(){
        if(page == 1){
            webView.loadUrl("file:///android_asset/gif_1.html");
        }else if(page == 2){
            webView.loadUrl("file:///android_asset/gif_2.html");
        }else if(page == 3){
            webView.loadUrl("file:///android_asset/gif_3.html");
        }
    }
}
