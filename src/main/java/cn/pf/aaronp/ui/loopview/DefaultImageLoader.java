package cn.pf.aaronp.ui.loopview;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * author: aaron.pf
 * date: 2019/3/12 20:30.
 * desc: DefaultImageLoader
 */

public class DefaultImageLoader implements LoopViewImageLoader {
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);
    }
}
