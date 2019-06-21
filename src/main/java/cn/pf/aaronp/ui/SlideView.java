
package cn.pf.aaronp.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;



/**
 * 向左滑动布局，显示删除（可自定义）等按键
 * SlideView 继承自LinearLayout
 */
public class SlideView extends LinearLayout {
    private static final int SCROLL_CALLBACK = 1;
    private Context mContext;

    // 用来放置所有view的容器
    private LinearLayout mViewContent;

    // 用来放置内置view的容器，比如删除 按钮
    private LinearLayout mHolder;

    // 弹性滑动对象，提供弹性滑动效果
    private Scroller mScroller;

    // 滑动回调接口，用来向上层通知滑动事件
    private OnSlideListener mOnSlideListener;

    // 内置容器的宽度 单位：dp
    private int mHolderWidth = 65;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    // 记录落点坐标
    private int mDownX = 0;
    private int mDownY = 0;

    // 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
    private static final int TAN = 2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SCROLL_CALLBACK) {
                reScroll();
            }
            super.handleMessage(msg);
        }
    };

    public interface OnSlideListener {
        // SlideView的三种状态： 关闭 滑动，打开，
        public enum SLIDE_STATUS {
            SLIDE_STATUS_OFF, SLIDE_STATUS_START_SCROLL, SLIDE_STATUS_ON
        };

        /**
         * @param view current SlideView
         * @param status SLIDE_STATUS_ON, SLIDE_STATUS_OFF or SLIDE_STATUS_START_SCROLL
         */
        public void onSlide(View view, SLIDE_STATUS status);
    }

    public SlideView(Context context) {
        super(context);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mContext = getContext();
        // 初始化弹性滑动对象
        mScroller = new Scroller(mContext);
        setOrientation(LinearLayout.HORIZONTAL);

        // 将slide_view_merge加载进来
        mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));

    }

    // 设置滑动回调
    public void setOnSlideListener(OnSlideListener onSlideListener) {
        mOnSlideListener = onSlideListener;
    }

    // 将当前状态置为关闭
    public void shrink() {
        if (getScrollX() != 0) {
            this.smoothScrollTo(0, 0);
        }
    }

    // 根据MotionEvent来进行滑动，这个方法的作用相当于onTouchEvent
    // 如果你不需要处理滑动冲突，可以直接重命名，照样能正常工作
    public void onRequireTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                mDownX = (int) event.getX();
                mDownY = (int) event.getY();


                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this,
                            OnSlideListener.SLIDE_STATUS.SLIDE_STATUS_START_SCROLL);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
                    break;
                }

                // 计算滑动终点是否合法，防止滑动越界
                int newScrollX = scrollX - deltaX;
                if (deltaX != 0) {
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    this.scrollTo(newScrollX, 0);
                }
                handler.removeMessages(SCROLL_CALLBACK);
                handler.sendEmptyMessageDelayed(SCROLL_CALLBACK, 500);
                break;
            }
            case MotionEvent.ACTION_UP: {

                int newScrollX = 0;
                // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
                if (scrollX - mHolderWidth * 0.75 > 0) {
                    newScrollX = mHolderWidth;
                }
                // 慢慢滑向终点
                this.smoothScrollTo(newScrollX, 0);

                handler.removeMessages(SCROLL_CALLBACK);
                // 通知上层滑动事件
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this,
                            newScrollX == 0 ? OnSlideListener.SLIDE_STATUS.SLIDE_STATUS_OFF
                                    : OnSlideListener.SLIDE_STATUS.SLIDE_STATUS_ON);
                }

                if (Math.abs(x - mDownX) < 15 && Math.abs(y - mDownY) < 15) {
                    if (mSlideClickListener != null) {
                        mSlideClickListener.onClick();
                    }
                }
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
    }

    private void smoothScrollTo(int destX, int destY) {
        // 缓慢滚动到指定位置
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 以三倍时长滑向destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    public void reScroll() {
        this.smoothScrollTo(0, mHolderWidth);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private SlideClickListener mSlideClickListener;

    public void setSlideClickListener(SlideClickListener slideClickListener) {
        mSlideClickListener = slideClickListener;
    }

    public interface SlideClickListener {
        public void onClick();
    }

}
