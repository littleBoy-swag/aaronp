package cn.pf.aaronp.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pf.aaronp.R;
import cn.pf.aaronp.utils.FunctionUtil;

/**
 * author: aaron.pf
 * date: 2019/3/8 14:59.
 * desc: 仿iOS的ActionSheet
 */

public class ActionSheet extends Dialog {
    private Context context;
    private LinearLayout mLayout;
    private TextView mTvTitle;
    private ArrayList<TextView> mSheetList;
    private TextView mTvCancel;

    private String mTitle;

    private String mCancelText;

    /**
     * 选择项文字列表
     */
    private ArrayList<String> mSheetTextList;

    private int mTitleTextColor;

    private int mCancelTextColor;

    private int mSheetTextColor;

    private int mTitleTextSize;

    private int mCancelTextSize;

    private int mSheetTextSize;

    private int mTitleHeight;

    private int mSheetHeight;

    private int mCancelHeight;

    /**
     * 底部间隔
     */
    private int mMarginBottom;

    private View.OnClickListener mCancelListener;

    private ArrayList<View.OnClickListener> mSheetListenerList;

    public ActionSheet(@NonNull Context context) {
        this(context, R.style.ActionSheetStyle);
        init(context);
    }

    public ActionSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        this.context = context;
        mCancelText = "取消";

        mTitleTextColor = Color.parseColor("#FFAAAAAA");
        mCancelTextColor = Color.parseColor("#FFFF0000");
        mSheetTextColor = Color.parseColor("#FF228FFF");
        mTitleTextSize = 14;
        mCancelTextSize = 16;
        mSheetTextSize = 16;
        mTitleHeight = FunctionUtil.dp2px(context, 42);
        mCancelHeight = FunctionUtil.dp2px(context, 42);
        mSheetHeight = FunctionUtil.dp2px(context, 42);
        mMarginBottom = FunctionUtil.dp2px(context, 10);

        mSheetList = new ArrayList<>();
        mSheetTextList = new ArrayList<>();
        mSheetListenerList = new ArrayList<>();
    }

    private ActionSheet createDialog() {
        mLayout = new LinearLayout(context);
        mLayout.setBackgroundColor(Color.parseColor("#00000000"));
        mLayout.setOrientation(LinearLayout.VERTICAL);

        if (mTitle != null) {
            mTvTitle = new TextView(context);
            mTvTitle.setGravity(Gravity.CENTER);
            mTvTitle.setText(mTitle);
            mTvTitle.setTextColor(mTitleTextColor);
            mTvTitle.setTextSize(mTitleTextSize);
            mTvTitle.setBackgroundResource(R.drawable.dialog_top_up);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTitleHeight);
            mLayout.addView(mTvTitle, lp);
        }

        for (int i = 0; i < mSheetTextList.size(); i++) {
            if (i == 0 && mTvTitle != null) {
                View topDivideLine = new View(context);
                topDivideLine.setBackgroundColor(Color.parseColor("#FF606060"));
                mLayout.addView(topDivideLine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FunctionUtil.dp2px(context, 1)));
            }
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(mSheetTextList.get(i));
            textView.setTextColor(mSheetTextColor);
            textView.setTextSize(mSheetTextSize);
            if (mTitle != null) {
                if (i == mSheetTextList.size() - 1) {
                    textView.setBackgroundResource(R.drawable.dialog_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.layout_white_selector);
                }
            } else {
                if (mSheetTextList.size() == 1) {
                    textView.setBackgroundResource(R.drawable.dialog_white_selector);
                } else {
                    if (i == 0) {
                        textView.setBackgroundResource(R.drawable.dialog_top_selector);
                    } else if (i == mSheetTextList.size() - 1) {
                        textView.setBackgroundResource(R.drawable.dialog_bottom_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.layout_white_selector);
                    }
                }
            }

            textView.setOnClickListener(mSheetListenerList.get(i));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mSheetHeight);
            mLayout.addView(textView, lp);
            mSheetList.add(textView);

            if (i != mSheetTextList.size() - 1) {
                View bottomDivideLine = new View(context);
                bottomDivideLine.setBackgroundColor(Color.parseColor("#FF6C6C6C"));
                mLayout.addView(bottomDivideLine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FunctionUtil.dp2px(context, 1)));
            }
        }

        mTvCancel = new TextView(context);
        mTvCancel.setGravity(Gravity.CENTER);
        mTvCancel.setText(mCancelText);
        mTvCancel.setTextColor(mCancelTextColor);
        mTvCancel.setTextSize(mCancelTextSize);
        mTvCancel.setBackgroundResource(R.drawable.dialog_white_selector);
        mTvCancel.setOnClickListener(mCancelListener);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mCancelHeight);
        lp.setMargins(0, FunctionUtil.dp2px(context, 10), 0, 0);
        mLayout.addView(mTvCancel, lp);

        if (getWindow() != null) {
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().getAttributes().y = mMarginBottom;
        }
        show();
        setContentView(mLayout);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        return this;
    }

    private void addMenuItem(String text, View.OnClickListener listener) {
        mSheetTextList.add(text);
        mSheetListenerList.add(listener);
    }

    private void setCancelText(String cancelText) {
        this.mCancelText = cancelText;
    }

    private void setCancelHeight(int cancelHeight) {
        this.mCancelHeight = cancelHeight;
    }

    private void setCancelTextColor(int cancelColor) {
        this.mCancelTextColor = cancelColor;
    }

    private void setCancelTextSize(int size) {
        this.mCancelTextSize = size;
    }

    private void setSheetHeight(int height) {
        this.mSheetHeight = height;
    }

    private void setSheetTextColor(int color) {
        this.mSheetTextColor = color;
    }

    private void setSheetTextSize(int size) {
        this.mSheetTextSize = size;
    }

    private void setTitle(String title) {
        this.mTitle = title;
    }

    private void setTitleHeight(int height) {
        this.mTitleHeight = height;
    }

    public void setTitleTextColor(int color) {
        this.mTitleTextColor = color;
    }

    private void setTitleTextSize(int size) {
        this.mTitleTextSize = size;
    }

    private void setMarginBottom(int bottom) {
        this.mMarginBottom = bottom;
    }

    private void addCancelListener(View.OnClickListener listener) {
        this.mCancelListener = listener;
    }

    public static class DialogBuilder {
        ActionSheet dialog;

        public DialogBuilder(Context context) {
            dialog = new ActionSheet(context);
        }

        public DialogBuilder addMenuItem(String text, View.OnClickListener listener) {
            dialog.addMenuItem(text, listener);
            return this;
        }

        public DialogBuilder setCancelText(String text) {
            dialog.setCancelText(text);
            return this;
        }

        public DialogBuilder setCancelHeight(int height) {
            dialog.setCancelHeight(height);
            return this;
        }

        public DialogBuilder setCancelColor(int color) {
            dialog.setCancelTextColor(color);
            return this;
        }

        public DialogBuilder setCancelTextSize(int size) {
            dialog.setCancelTextSize(size);
            return this;
        }

        public DialogBuilder setSheetHeight(int height) {
            dialog.setSheetHeight(height);
            return this;
        }

        public DialogBuilder setSheetTextColor(int color) {
            dialog.setSheetTextColor(color);
            return this;
        }

        public DialogBuilder setSheetTextSize(int size) {
            dialog.setSheetTextSize(size);
            return this;
        }

        public DialogBuilder setTitle(String text) {
            dialog.setTitle(text);
            return this;
        }

        public DialogBuilder setTitleHeight(int height) {
            dialog.setTitleHeight(height);
            return this;
        }

        public DialogBuilder setTitleColor(int color) {
            dialog.setTitleTextColor(color);
            return this;
        }

        public DialogBuilder setTitleSize(int size) {
            dialog.setTitleTextSize(size);
            return this;
        }

        public DialogBuilder setMarginBottom(int bottom) {
            dialog.setMarginBottom(bottom);
            return this;
        }

        public DialogBuilder addCancelListener(View.OnClickListener listener) {
            dialog.addCancelListener(listener);
            return this;
        }

        public ActionSheet create() {
            return dialog.createDialog();
        }

    }

}
