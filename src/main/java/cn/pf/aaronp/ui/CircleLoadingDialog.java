package cn.pf.aaronp.ui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.pf.aaronp.R;

/**
 * author: aaron.pf
 * date: 2019/3/8 21:33.
 * desc:
 */

public class CircleLoadingDialog extends DialogFragment {

    private TextView mTvLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_loading, container, false);
        mTvLoading = view.findViewById(R.id.tv_loading);
        return view;
    }

    private static CircleLoadingDialog newInstance() {
        CircleLoadingDialog dialog = new CircleLoadingDialog();
        dialog.setStyle(STYLE_NO_FRAME, R.style.circle_loading_dialog);
        return dialog;
    }

    public static CircleLoadingDialog showDialog(FragmentManager fragmentManager) {
        CircleLoadingDialog dialog = CircleLoadingDialog.newInstance();
        dialog.show(fragmentManager, "CircleLoadingDialog");
        return dialog;
    }

}
