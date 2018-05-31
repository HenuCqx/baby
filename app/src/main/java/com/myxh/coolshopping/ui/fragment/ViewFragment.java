package com.myxh.coolshopping.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.VideoView;

import com.myxh.coolshopping.R;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.ui.base.BaseFragment;
import com.yolanda.nohttp.rest.Response;

public class ViewFragment extends BaseFragment implements HttpListener<String> {

    private VideoView videoView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look, null);
        initPopMenu();
        WebView webView = (WebView)view.findViewById(R.id.webgif);

        WebSettings webSettings = webView.getSettings();

        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setInitialScale(100);
        webView.loadUrl("file:///android_asset/gif.html");
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



}
