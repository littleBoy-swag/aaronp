package cn.pf.aaronp.base.example;

import cn.pf.aaronp.base.BasePresenter;

/**
 * Created by aaron pan on 2019/5/24.
 */

public class LoginPresenter extends BasePresenter<LoginModel, LoginContract.LoginView> implements LoginContract.LoginPresent, LoginDataSource.OnLoginFinishListener {
    @Override
    public void loading() {
        mIView.showLoading();
    }

    @Override
    public void success() {
        mIView.loadResult("成功");
        mIView.loadFinish();
    }

    @Override
    public void error() {
        mIView.loadResult("失败");
        mIView.loadFinish();
    }

    @Override
    public void loadData() {
        mIModel.load(this);
    }

    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }
}
