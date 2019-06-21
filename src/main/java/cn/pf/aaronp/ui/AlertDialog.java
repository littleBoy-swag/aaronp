package cn.pf.aaronp.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pf.aaronp.R;

/**
 * author: aaron.pf
 * date: 2019/3/11 10:56.
 * desc:仿ios的alert dialog
 * <p>
 * 调用示例:
 * new AlertDialog(this).builder().initDialog()
 * .setCancelable(false)...
 */

public class AlertDialog {
    private Context mContext;
    private Dialog mDialog;
    private LinearLayout mLinearLayout;
    private TextView mTvTitle, mTvContent;
    private Button mBtnNegative, mBtnPositive;
    private View mViewLine, mViewLineVertical;
    private Display mDisplay;
    private boolean showTitle = false;
    private boolean showContent = false;
    private boolean showPositiveBtn = false;
    private boolean showNegativeBtn = false;

    public AlertDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            mDisplay = windowManager.getDefaultDisplay();
        }
    }

    public AlertDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_alert_dialog, null);
        mLinearLayout = view.findViewById(R.id.ll_bg_alert_dialog);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvContent = view.findViewById(R.id.tv_content);
        mBtnNegative = view.findViewById(R.id.btn_negative);
        mBtnPositive = view.findViewById(R.id.btn_positive);
        mViewLine = view.findViewById(R.id.view_line);
        mViewLineVertical = view.findViewById(R.id.view_line_vertical);
        initDialog();
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view);
        Point point = new Point();
        mDisplay.getSize(point);
        int width = point.x;
        mLinearLayout.setLayoutParams(new FrameLayout.LayoutParams((int) (width * 0.8), FrameLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    private AlertDialog initDialog() {
        if (mLinearLayout != null) {
            mTvTitle.setVisibility(View.GONE);
            mTvContent.setVisibility(View.GONE);
            mBtnPositive.setVisibility(View.GONE);
            mBtnNegative.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
            mViewLineVertical.setVisibility(View.GONE);
        }
        showContent = false;
        showPositiveBtn = false;
        showNegativeBtn = false;
        showTitle = false;
        return this;
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setText("");
        } else {
            mTvTitle.setText(title);
        }
        return this;
    }

    public AlertDialog setContent(String content) {
        showContent = true;
        if (TextUtils.isEmpty(content)) {
            mTvContent.setText("");
        } else {
            mTvContent.setText(content);
        }
        return this;
    }

    public AlertDialog setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    public AlertDialog setNegativeButton(String negativeText, View.OnClickListener listener) {
        return setNegativeButton(negativeText, -1, listener);
    }

    public AlertDialog setNegativeButton(String negativeText, int color, final View.OnClickListener listener) {
        showNegativeBtn = true;
        if (TextUtils.isEmpty(negativeText)) {
            mBtnNegative.setText("");
        } else {
            mBtnNegative.setText(negativeText);
        }
        if (color == -1) {
            color = R.color.color_btn_alert_dialog;
        }
        mBtnNegative.setTextColor(ContextCompat.getColor(mContext, color));
        mBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                dismiss();
            }
        });
        return this;
    }

    public AlertDialog setPositiveButton(String positiveText, View.OnClickListener listener) {
        return setPositiveButton(positiveText, -1, listener);
    }

    private AlertDialog setPositiveButton(String positiveText, int color, final View.OnClickListener listener) {
        showPositiveBtn = true;
        if (TextUtils.isEmpty(positiveText)) {
            mBtnPositive.setText("");
        } else {
            mBtnPositive.setText(positiveText);
        }
        if (color == -1) {
            color = R.color.color_btn_alert_dialog;
        }
        mBtnPositive.setTextColor(ContextCompat.getColor(mContext, color));
        mBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showContent) {
            mTvTitle.setText("");
            mTvTitle.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            mTvTitle.setVisibility(View.VISIBLE);
        }
        if (showContent) {
            mTvContent.setVisibility(View.VISIBLE);
        }
        if (!showPositiveBtn && !showNegativeBtn) {
            mBtnPositive.setText("");
            mBtnPositive.setVisibility(View.VISIBLE);
            mViewLineVertical.setVisibility(View.GONE);
            mBtnPositive.setBackgroundResource(R.drawable.alert_dialog_selector);
            mBtnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        if (showPositiveBtn && showNegativeBtn) {
            mViewLineVertical.setVisibility(View.VISIBLE);
            mBtnPositive.setVisibility(View.VISIBLE);
            mBtnPositive.setBackgroundResource(R.drawable.alert_dialog_right_selector);
            mBtnNegative.setVisibility(View.VISIBLE);
            mBtnNegative.setBackgroundResource(R.drawable.alert_dialog_left_selector);
            mViewLine.setVisibility(View.VISIBLE);
        }
        if (showPositiveBtn && !showNegativeBtn) {
            mViewLineVertical.setVisibility(View.GONE);
            mBtnPositive.setVisibility(View.VISIBLE);
            mBtnPositive.setBackgroundResource(R.drawable.alert_dialog_selector);
        }
        if (showNegativeBtn && !showPositiveBtn) {
            mViewLineVertical.setVisibility(View.GONE);
            mBtnNegative.setVisibility(View.VISIBLE);
            mBtnNegative.setBackgroundResource(R.drawable.alert_dialog_selector);
        }
    }

    public void show() {
        setLayout();
        mDialog.show();
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
