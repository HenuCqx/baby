package com.myxh.coolshopping.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.common.AppConstant;
import com.myxh.coolshopping.common.BmobManager;
import com.myxh.coolshopping.common.CoolApplication;
import com.myxh.coolshopping.entity.GoodsDetailInfo;
import com.myxh.coolshopping.listener.BmobQueryCallback;
import com.myxh.coolshopping.model.BaseModel;
import com.myxh.coolshopping.model.FavorModel;
import com.myxh.coolshopping.model.User;
import com.myxh.coolshopping.network.CallServer;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.ui.base.BaseActivity;
import com.myxh.coolshopping.ui.fragment.HomeFragment;
import com.myxh.coolshopping.ui.widget.ObserverScrollView;
import com.myxh.coolshopping.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DetailActivity extends BaseActivity implements View.OnClickListener, HttpListener<String>,ObserverScrollView.ScrollViewListener {

    private static final int REQUEST_GOOD = 300;
    public static final String DETAIL_INFO = "detailInfo";
    private static final String TAG = DetailActivity.class.getSimpleName();
    private SimpleDraweeView mProductPhoto;
    private TextView mTvProductName;
    private TextView mTvDescription;
    private TextView mTvBought;
    private LinearLayout mSureLayoutAnytime;
    private LinearLayout mSureLayoutOverdue;
    private LinearLayout mSureLayoutSevenday;
    private TextView mTvMerchantTitle;
    private TextView mMerchantTvAddress;
    private TextView mMerchantTvHours;
    private TextView mMerchantTvDistance;
    private ImageView mMerchantIvCall;
    private WebView mDescWvDescription;
    private RelativeLayout mDescCheckDetailLayout;
    private WebView mDescWvTips;
    private ListView mDescListRecommend;
    private ImageView mTitleIvBack;
    private TextView mTitleTvTitle;
    private ImageView mTitleIvFavorite;
    private ImageView mTitleIvShare;
    private LinearLayout mTitleLayout;
    private TextView mLayoutBuyPrice;
    private TextView mLayoutBuyValue;
    private Button mLayoutBuyBtn;
    private RelativeLayout mLayoutBuy;
    private String mGoodsId;
    private int mGoodsBought;
    private String mSevenRefund;
    private int mTimeRefund;
    private GoodsDetailInfo mDetailInfo;

    private ObserverScrollView mScrollView;
    private int mPhotoHeight;

    //网络是否请求成功
    private boolean isRequestSuccess = false;

    //是否收藏
    private boolean isFavor = false;
    //收藏按钮是否点击
    private boolean isClickFavor = false;
    private FavorModel mFavoredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WebView webView = (WebView)findViewById(R.id.webia);
//        WebSettings settings = webView.getSettings();
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        webView.setInitialScale(100);
        webView.loadUrl("http://news.ts.cn/system/2018/05/10/035204892.shtml");
    }

    private void setViewWithIntentData() {
        mTvBought.setText(""+mGoodsBought);
        if (mSevenRefund.equals("1")) {
            mSureLayoutSevenday.setVisibility(View.VISIBLE);
        } else {
            mSureLayoutSevenday.setVisibility(View.INVISIBLE);
        }
        if (mTimeRefund == 1) {
            mSureLayoutAnytime.setVisibility(View.VISIBLE);
            mSureLayoutOverdue.setVisibility(View.VISIBLE);
        } else {
            mSureLayoutAnytime.setVisibility(View.INVISIBLE);
            mSureLayoutOverdue.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        mGoodsId = getIntent().getExtras().getString(HomeFragment.GOODS_ID);
        mGoodsBought = getIntent().getExtras().getInt(HomeFragment.GOODS_BOUGHT);
        mSevenRefund = getIntent().getExtras().getString(HomeFragment.GOODS_SEVEN_REFUND);
        mTimeRefund = getIntent().getExtras().getInt(HomeFragment.GOODS_TIME_REFUND);
        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"jsonid" , RequestMethod.GET);
        CallServer.getInstance().add(this,REQUEST_GOOD,request,this,true,true);
    }

    /**
     * 设置滚动监听
     */
    private void initScrollViewListener() {
        ViewTreeObserver treeObserver = mProductPhoto.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mProductPhoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPhotoHeight = mProductPhoto.getHeight();
                mScrollView.setScrollListener(DetailActivity.this);
            }
        });
    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }

    /**
     * 滑动监听
     * @param scrollView
     * @param x
     * @param y
     * @param oldX
     * @param oldY
     */
    @Override
    public void onScroll(ObserverScrollView scrollView, int x, int y, int oldX, int oldY) {

    }

    /**
     * 社会化分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        if (isRequestSuccess) {
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            oks.setTitle(mDetailInfo.getResult().getProduct());
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl(mDetailInfo.getResult().getImages().get(0).getImage());
            // text是分享文本，所有平台都需要这个字段
            oks.setText(mDetailInfo.getResult().getTitle());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://sharesdk.cn");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(mDetailInfo.getResult().getImages().get(0).getImage());
            // 启动分享GUI
            oks.show(this);
        }
    }
}
