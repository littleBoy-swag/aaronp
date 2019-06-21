package cn.pf.aaronp.scan.decoding;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * author: aaron.pf
 * date: 2019/3/12 11:25.
 * desc:
 */

public class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        run();
    }

    @Override
    public void run() {
        activityToFinish.finish();
    }

}
