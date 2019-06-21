package cn.pf.aaronp.base.example;


import android.os.Handler;

import cn.pf.aaronp.base.BaseModel;

/**
 * Created by aaron pan on 2019/5/24.
 */

public class LoginModel extends BaseModel implements LoginDataSource {
    @Override
    public void load(final OnLoginFinishListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.success();
            }
        }, 2000);
    }
}
