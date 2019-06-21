package cn.pf.aaronp.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * author: aaron.pf
 * date: 2019/3/13 9:21.
 * desc: 具有阻尼效果的ScrollView
 */

public class BounceScrollView extends ScrollView {

    private int preY = 0;
    private int startY = 0;
    private int curY = 0;
    private int deltaY = 0;
    private View childView;
    private Rect topRect = new Rect();
    private float moveHeight;


    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (childView == null) {
            return super.dispatchTouchEvent(ev);
        }

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                preY = startY;
                topRect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                moveHeight = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                curY = (int) ev.getY();
                deltaY = curY - preY;
                preY = curY;
                boolean flag = (!childView.canScrollVertically(-1) && (curY - startY) > 0) || (!childView.canScrollVertically(1) && (curY - startY) < 0);
                if (flag) {
                    float distance = curY - startY;
                    if (distance < 0) {
                        distance *= -1;
                    }
                    //阻尼值
                    float damping = 0.5f;
                    float height = getHeight();
                    if (height != 0) {
                        if (distance > height) {
                            damping = 0;
                        } else {
                            damping = (height - distance) / height;
                        }
                    }
                    if (curY - startY < 0) {
                        damping = 1 - damping;
                    }
                    damping *= 0.25;
                    damping += 0.25;
                    moveHeight = moveHeight + (deltaY * damping);
                    childView.layout(topRect.left, (int) (topRect.top + moveHeight), topRect.right, (int) (topRect.bottom + moveHeight));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!topRect.isEmpty()) {
                    upDownMoveAnimation();
                    childView.layout(topRect.left, topRect.top, topRect.right, topRect.bottom);
                }
                startY = 0;
                curY = 0;
                topRect.setEmpty();
                break;
            default:
        }

        return super.dispatchTouchEvent(ev);
    }

    private void upDownMoveAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, childView.getTop(), topRect.top);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DampInterpolator());
        childView.setAnimation(translateAnimation);
    }

    private class DampInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float v) {
            //先快后慢，为了更快更慢的效果，多乘了几次，现在这个效果比较满意
            return 1 - (1 - v) * (1 - v) * (1 - v) * (1 - v) * (1 - v);
        }
    }
}
