package com.myxh.coolshopping.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myxh.coolshopping.R;

public class ZeYuanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zeyuan);
        WebView webView = (WebView)findViewById(R.id.around_listView);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
