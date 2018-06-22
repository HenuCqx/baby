package com.myxh.baby.listener;

import com.myxh.baby.model.BaseModel;
import com.myxh.baby.model.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface IBmobListener {

    void onMsgSendSuccess();
    void onMsgSendFailure();
    void onLoginSuccess();
    void onLoginFailure();
    void onSignUpSuccess(User user);
    void onSignUpFailure(BmobException e);
    void onQuerySuccess(List<? extends BaseModel> dataList);
    void onQueryFailure(BmobException e);
}
