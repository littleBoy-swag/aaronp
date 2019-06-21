package cn.pf.aaronp.base.example;

import cn.pf.aaronp.base.IBaseView;

/**
 * Created by aaron pan on 2019/5/24.
 */

public interface LoginContract extends IBaseView {

    interface LoginPresent {
        void loadData();
    }

    interface LoginView {
        void loadResult(String result);

        void showLoading();

        void loadFinish();
    }

}
