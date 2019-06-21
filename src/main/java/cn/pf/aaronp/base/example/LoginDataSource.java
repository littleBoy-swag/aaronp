package cn.pf.aaronp.base.example;

/**
 * Created by aaron pan on 2019/5/24.
 */

public interface LoginDataSource {
    interface OnLoginFinishListener {
        void loading();

        void success();

        void error();
    }

    void load(OnLoginFinishListener listener);
}
