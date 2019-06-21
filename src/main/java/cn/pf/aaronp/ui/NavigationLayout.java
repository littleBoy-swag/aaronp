package cn.pf.aaronp.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.pf.aaronp.R;

/**
 * author: aaron.pf
 * date: 2019/3/11 16:58.
 * desc: 底部的tab栏
 */

public class NavigationLayout extends LinearLayout implements View.OnClickListener {

    /**
     * 保存TabView
     */
    private List<View> mTabViews;
    /**
     * 保存Tab
     */
    private List<Tab> mTabs;

    private OnTabSelectedListener mOnTabSelectedListener;

    public void setTabSelectedListener(OnTabSelectedListener listener) {
        mOnTabSelectedListener = listener;
    }

    public NavigationLayout(Context context) {
        super(context);
        init();
    }

    public NavigationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mTabs = new ArrayList<>();
        mTabViews = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnTabSelectedListener != null) {
            mOnTabSelectedListener.onTabSelected(view, position);
        }
        updateState(position);
    }

    /**
     * 增加一个tab
     *
     * @param tab tab
     */
    public void addTab(Tab tab) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_tab, null);
        TextView tvIcon = view.findViewById(R.id.tv_icon);
        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(tab.mNormalIconResId);
        tvIcon.setTextColor(tab.mNormalTextColor);
        tvIcon.setText(tab.mText);

        view.setTag(mTabViews.size());
        view.setOnClickListener(this);

        mTabViews.add(view);
        mTabs.add(tab);

        addView(view);
    }

    public void setCurrentItem(int position) {
        if (position >= mTabs.size() || position < 0) {
            position = 0;
        }
        mTabViews.get(position).performClick();
        updateState(position);
    }

    /**
     * 更新图标状态
     *
     * @param position position
     */
    private void updateState(int position) {
        for (int i = 0; i < mTabViews.size(); i++) {
            View view = mTabViews.get(i);
            TextView textView = (TextView) view.findViewById(R.id.tv_icon);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
            if (i == position) {
                imageView.setImageResource(mTabs.get(i).mPressedIconResId);
                textView.setTextColor(getResources().getColor(mTabs.get(i).mPressedTextColor));
            } else {
                imageView.setImageResource(mTabs.get(i).mNormalIconResId);
                textView.setTextColor(getResources().getColor(mTabs.get(i).mNormalTextColor));
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < mTabViews.size(); i++) {
            View view = mTabViews.get(i);
            int width = getResources().getDisplayMetrics().widthPixels / mTabs.size();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabViews != null) {
            mTabViews.clear();
        }
        if (mTabs != null) {
            mTabs.clear();
        }
    }

    public static class Tab {
        private int mNormalIconResId;
        private int mPressedIconResId;
        private int mNormalTextColor;
        private int mPressedTextColor;
        private String mText;

        public Tab setText(String text) {
            mText = text;
            return this;
        }

        public Tab setNormalIconResId(int resId) {
            mNormalIconResId = resId;
            return this;
        }

        public Tab setPressedIconResId(int resId) {
            mPressedIconResId = resId;
            return this;
        }

        public Tab setNormalTextColor(int color) {
            mNormalTextColor = color;
            return this;
        }

        public Tab setPressedTextColor(int color) {
            mPressedTextColor = color;
            return this;
        }
    }

    public interface OnTabSelectedListener {
        /**
         * 选中
         *
         * @param view     view
         * @param position position
         */
        void onTabSelected(View view, int position);
    }

}
