package cn.pf.aaronp.ui.pickview.date;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aaron pan on 2019/5/27.
 */

public class DatePickerView {

    private static DatePickerView instance;

    private DatePickerView() {
    }

    public static DatePickerView getInstance() {
        if (instance == null) {
            synchronized (DatePickerView.class) {
                if (instance == null) {
                    instance = new DatePickerView();
                }
            }
        }
        return instance;
    }

    public void showDatePickerView(Activity activity, final OnDateSelectedListener listener) {
        TimePickerView timePickerView = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (listener != null) {
                    listener.onDateSelected(date, v);
                }
            }
        })
                .build();
        timePickerView.show();
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Date date, View v);
    }

}
