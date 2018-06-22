package com.myxh.baby.listener;

import com.myxh.baby.model.BaseModel;
import com.myxh.baby.model.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public abstract class BmobLoginCallback implements IBmobListener {
    @Override
    public void onMsgSendSuccess() {

    }

    @Override
    public void onMsgSendFailure() {

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
