package cn.pf.aaronp.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;


import cn.pf.aaronp.R;

/**
 * Created by aaron pan on 2019/5/31.
 * eg.
 * LoadingDialog loading = new LoadingDialog(getActivity());
 * loading.show();
 * loading.dismiss();
 */

public class LoadingDialog extends Dialog {

    private Context context;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.fragment_loading);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
        animation.setInterpolator(new LinearInterpolator());
        findViewById(R.id.img_loading_dialog).startAnimation(animation);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.8f;
            getWindow().setAttributes(params);
        }
    }
}
