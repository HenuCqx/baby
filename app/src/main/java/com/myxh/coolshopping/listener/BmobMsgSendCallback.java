package com.myxh.coolshopping.listener;

import com.myxh.coolshopping.model.BaseModel;
import com.myxh.coolshopping.model.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;


public abstract class BmobMsgSendCallback implements IBmobListener {

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onSignUpSuccess(User user) {

    }

    @Override
    public void onSignUpFailure(BmobException e) {

    }

    @Override
    public void onQuerySuccess(List<? extends BaseModel> dataList) {

    }

    @Override
    public void onQueryFailure(BmobException e) {

    }
}
