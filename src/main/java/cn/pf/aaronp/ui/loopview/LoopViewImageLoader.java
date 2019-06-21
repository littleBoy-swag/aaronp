package cn.pf.aaronp.ui.loopview;

import android.content.Context;
import android.widget.ImageView;

/**
 * author: aaron.pf
 * date: 2019/3/12 19:26.
 * desc:
 */

public interface LoopViewImageLoader {
    void displayImage(Context context, String url, ImageView imageView, int width, int height);
}
