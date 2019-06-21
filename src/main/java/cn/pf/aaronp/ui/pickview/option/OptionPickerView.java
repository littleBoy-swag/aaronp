package cn.pf.aaronp.ui.pickview.option;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.List;


/**
 * Created by aaron pan on 2019/5/27.
 */

public class OptionPickerView {

    private static OptionPickerView instance;

    private OptionPickerView() {
    }

    public static OptionPickerView getInstance() {
        if (instance == null) {
            synchronized (OptionPickerView.class) {
                if (instance == null) {
                    instance = new OptionPickerView();
                }
            }
        }
        return instance;
    }

    public void showTimePickerView(Activity activity, final List<String> list, final OnMySingleOptionListener listener) {

        OptionsPickerView opv = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (listener != null) {
                    listener.onSingleOptionSelected(list.get(options1), options1);
                }
            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        opv.setPicker(list);
        opv.show();
    }

    public interface OnMySingleOptionListener {
        void onSingleOptionSelected(String text, int position);
    }

}
