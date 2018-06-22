package com.myxh.baby.listener;

import com.myxh.baby.model.BaseModel;

import java.util.List;

import cn.bmob.v3.exception.BmobException;


public abstract class BmobSignUpCallback implements IBmobListener {
    @Override
    public void onMsgSendSuccess() {

    }

    @Override
    public void onMsgSendFailure() {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onQuerySuccess(List<? extends BaseModel> dataList) {

    }

    @Override
    public void onQueryFailure(BmobException e) {

    }
}
