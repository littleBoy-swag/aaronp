package cn.pf.aaronp.base.example;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.pf.aaronp.base.BaseActivity;

/**
 * Created by aaron pan on 2019/5/24.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载数据
        mPresenter.loadData();
    }

    @Override
    public void loadResult(String result) {
        //结果返回，在界面展示
    }

    @Override
    public void showLoading() {
        mPresenter.loading();
        //正在加载。。。
    }

    @Override
    public void loadFinish() {
        //加载结束
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
}
