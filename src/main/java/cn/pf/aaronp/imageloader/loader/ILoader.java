package cn.pf.aaronp.imageloader.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;

import cn.pf.aaronp.imageloader.config.SingleConfig;
import cn.pf.aaronp.imageloader.utils.DownLoadImageService;

/**
 * author: aaron.pf
 * date: 2019/3/12 14:48.
 * desc:
 */

public interface ILoader {

    void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD);

    void request(SingleConfig config);

    void pause();

    void resume();

    void clearDiskCache();

    void clearMomoryCache(View view);

    void clearMomory();

    boolean  isCached(String url);

    void trimMemory(int level);

    void clearAllMemoryCaches();

    void saveImageIntoGallery(DownLoadImageService downLoadImageService);

}
