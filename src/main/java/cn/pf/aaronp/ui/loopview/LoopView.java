package cn.pf.aaronp.ui.loopview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pf.aaronp.R;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * author: aaron.pf
 * date: 2019/3/12 19:23.
 * desc: 无限循环的轮播图
 */

public class LoopView extends FrameLayout implements View.OnClickListener {
    private Context context;
    private ImageView[] allPage;
    private ViewPager mViewPager;
    private LinearLayout dotLayout;

    /**
     * 自定义轮播图资源
     */
    private List<String> imgUrlList = new ArrayList<>();
    private List<String> linkUrlList = new ArrayList<>();
    private int pageIndicatorGravity = Gravity.CENTER;
    private int indicatorDrawableChecked = R.drawable.img_banner_dot_focused;
    private int indicatorDrawableUnchecked = R.drawable.img_banner_dot_normal;
    private int indicatorBackground = Color.TRANSPARENT;
    /**
     * 自动轮播启动开关
     */
    private boolean autoPlay = true;
    /**
     * 轮播指示器开关
     */
    private boolean showIndicator = true;
    private long duration = 5 * 1000L;
    /**
     * 轮播指示器之间的距离
     */
    private int dotMargin = 4;
    private int currentIndex = 0;
    private int[] distance = new int[4];
    /**
     * 图片加载
     */
    private LoopViewImageLoader loopViewImageLoader = new DefaultImageLoader();
    /**
     * 页面切换动画
     */
    private ViewPager.PageTransformer pageTransformer = new DepthPageTransformer();
    /**
     * 刷新当前页回调
     */
    private OnCurrentPageListener onCurrentPageListener;

    private static final int HANDLE_TEXT_CHANGED_MSG = 0x0001;

    @SuppressLint("HandlerLeak")
    private Handler viewPagerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem((currentIndex + 1) % allPage.length);
            viewPagerHandler.sendEmptyMessageDelayed(HANDLE_TEXT_CHANGED_MSG, duration);
        }
    };

    public LoopView(@NonNull Context context) {
        this(context, null);
    }

    public LoopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initViewPager() {
        if (imgUrlList == null || imgUrlList.size() < 1) {
            return;
        }

        LayoutInflater.from(context).inflate(R.layout.layout_loop_view, this, true);
        dotLayout = findViewById(R.id.ll_dot);
        mViewPager = findViewById(R.id.vp_loop);
        dotLayout.removeAllViews();

        if (loopViewImageLoader == null) {
            throw new NullPointerException("LoopViewImageLoader == null");
        }

        allPage = new ImageView[imgUrlList.size()];
        //dot个数和页面个数相等
        for (int i = 0; i < imgUrlList.size(); i++) {
            ImageView imageView = new ImageView(context);
            allPage[i] = imageView;
            imageView.setTag(R.id.LoopView_img, i);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            loopViewImageLoader.displayImage(context, imgUrlList.get(i), imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setOnClickListener(this);
        }

        if (showIndicator) {
            drawIndicator();
        }

        PagerAdapter pagerAdapter = new LoopViewPagerAdapter();
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setFocusable(true);
        if (pageTransformer != null) {
            mViewPager.setPageTransformer(true, pageTransformer);
        }
        mViewPager.addOnPageChangeListener(new LoopViewPagerChangeListener());

    }

    /**
     * 绘制指示点
     */
    private void drawIndicator() {
        if (imgUrlList.size() <= 1) {
            return;
        }
        for (int i = 0; i < imgUrlList.size(); i++) {
            ImageView dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = dotMargin;
            params.rightMargin = dotMargin;
            dot.setBackground(getResources().getDrawable(indicatorDrawableUnchecked));
            dotLayout.addView(dot, params);
        }
        dotLayout.setGravity(pageIndicatorGravity);
        dotLayout.setPadding(distance[0], distance[1], distance[2], distance[3]);
        dotLayout.setBackgroundColor(indicatorBackground);
        dotLayout.getChildAt(0).setBackground(getResources().getDrawable(indicatorDrawableChecked));
    }


    @Override
    public void onClick(View view) {
        if (onCurrentPageListener != null) {
            onCurrentPageListener.onPageClick(imgUrlList, linkUrlList, (int) view.getTag(R.id.LoopView_img));
        }
    }

    private class LoopViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return allPage == null ? 0 : allPage.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(allPage[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = allPage[position];
            container.addView(imageView);
            return imageView;
        }
    }

    private class LoopViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        private boolean scrolled;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setCurrentDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                //手势滑动
                case SCROLL_STATE_DRAGGING:
                    scrolled = false;
                    break;
                //页面切换
                case SCROLL_STATE_SETTLING:
                    scrolled = true;
                    break;
                //滑动结束
                case SCROLL_STATE_IDLE:
                    //当前为最后一张
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1
                            && !scrolled) {
                        mViewPager.setCurrentItem(0);
                    } else if (mViewPager.getCurrentItem() == 0 && !scrolled) {
                        //当前为第一张
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                    }
                    break;
                default:
            }
        }
    }

    private void setCurrentDot(int position) {
        currentIndex = position;
        if (onCurrentPageListener != null) {
            onCurrentPageListener.onPageSelected(position);
        }
        if (!showIndicator) {
            return;
        }
        for (int i = 0; i < dotLayout.getChildCount(); i++) {
            if (i == position) {
                dotLayout.getChildAt(position).setBackground(getResources().getDrawable(indicatorDrawableChecked));
            } else {
                dotLayout.getChildAt(i).setBackground(getResources().getDrawable(indicatorDrawableUnchecked));
            }
        }
    }

    public interface OnCurrentPageListener {
        void onPageSelected(int position);

        void onPageClick(@NonNull List<String> imgUrlList, @NonNull List<String> linkUrlList, int position);
    }

    public void setLoopViewPagerListener(OnCurrentPageListener onCurrentPageListener) {
        this.onCurrentPageListener = onCurrentPageListener;
    }

    public LoopView setImageUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
        return this;
    }

    public LoopView setLinkUrlList(List<String> linkUrlList) {
        this.linkUrlList = linkUrlList;
        return this;
    }

    public LoopView setImageLoader(LoopViewImageLoader loopViewImageLoader) {
        this.loopViewImageLoader = loopViewImageLoader;
        return this;
    }

    public LoopView setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        return this;
    }

    public LoopView setPagerTransformer(ViewPager.PageTransformer pagerTransformer) {
        this.pageTransformer = pagerTransformer;
        return this;
    }

    public LoopView setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
        return this;
    }

    public LoopView setIndicatorGravity(int gravity) {
        this.pageIndicatorGravity = gravity;
        return this;
    }

    public LoopView setDotMargin(int margin) {
        this.dotMargin = margin;
        return this;
    }

    public LoopView setIndicatorDrawableChecked(@DrawableRes int resId) {
        this.indicatorDrawableChecked = resId;
        return this;
    }

    public LoopView setIndicatorDrawableUnchecked(@DrawableRes int resId) {
        this.indicatorDrawableUnchecked = resId;
        return this;
    }

    public LoopView setIndicatorBackground(int resId) {
        this.indicatorBackground = resId;
        return this;
    }

    public LoopView setIndicatorPadding(int left, int top, int right, int bottom) {
        distance[0] = left;
        distance[1] = top;
        distance[2] = right;
        distance[3] = bottom;
        return this;
    }

    public ViewPager getViewpager() {
        return mViewPager;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void start(long duration) {
        this.duration = duration;
        initViewPager();
        restart();
    }

    public void start() {
        initViewPager();
        restart();
    }

    public void stop() {
        if (viewPagerHandler != null) {
            viewPagerHandler.removeMessages(HANDLE_TEXT_CHANGED_MSG);
        }
    }

    public void restart() {
        if (autoPlay && imgUrlList != null && imgUrlList.size() > 1) {
            stop();
            viewPagerHandler.sendEmptyMessageDelayed(HANDLE_TEXT_CHANGED_MSG, duration);
        }
    }

}
