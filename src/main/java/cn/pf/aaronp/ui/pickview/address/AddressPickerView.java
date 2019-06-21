package cn.pf.aaronp.ui.pickview.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.pf.aaronp.KitApp;

/**
 * Created by aaron pan on 2019/5/27.
 *
 * @Author aaron
 */

public class AddressPickerView {

    private static final int PARSE_SUCCESS = 0x12;

    private static AddressPickerView instance;

    private OnMyPickerViewSelectedListener listener;

    private String title;

    private Activity activity;

    private List<Province> options1Items = new ArrayList<>();
    //city
    private List<ArrayList<String>> options2Items = new ArrayList<>();
    //region
    private List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private AddressPickerView() {
    }

    public static AddressPickerView getInstance() {
        if (instance == null) {
            synchronized (AddressPickerView.class) {
                if (instance == null) {
                    instance = new AddressPickerView();
                }
            }
        }
        return instance;
    }

    @SuppressLint("HandlerLeak")
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == PARSE_SUCCESS) {
                showPickerView();
            }
        }
    };

    /**
     * 解析json城市数据
     */
    public void parseData() {
        //获取assets下的province.json
        String jsonStr = GetJsonUtil.getJson(KitApp.getContext(), "province.json");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Province>>() {
        }.getType();
        List<Province> provinceList = gson.fromJson(jsonStr, type);
        //把解析的数组组装成list
        options1Items = provinceList;
        //遍历省
        for (int i = 0; i < provinceList.size(); i++) {
            //存城市
            ArrayList<String> cityList = new ArrayList<>();
            //存区
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();

            //遍历市
            for (int j = 0; j < provinceList.get(i).city.size(); j++) {
                //取到城市名称
                String cityName = provinceList.get(i).city.get(j).name;
                cityList.add(cityName);

                //该城市的所有地区列表
                ArrayList<String> cityAreaList = new ArrayList<>();
                if (provinceList.get(i).city.get(j).area == null || provinceList.get(i).city.get(j).area.size() == 0) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(provinceList.get(i).city.get(j).area);
                }
                provinceAreaList.add(cityAreaList);
            }

            //添加城市数据
            options2Items.add(cityList);

            //添加地区数据
            options3Items.add(provinceAreaList);
        }
        //解析完后发送解析成功的通知
        myHandler.sendEmptyMessage(PARSE_SUCCESS);
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是选中的位置
                String province = options1Items.get(options1).name;
                String city = options2Items.get(options1).get(options2);
                String area = options3Items.get(options1).get(options2).get(options3);
                if (listener != null) {
                    listener.onPickerViewSelected(province, city, area);
                }
            }
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    /**
     * 设置listener 需要在showPickView之前调用
     *
     * @param listener listener
     */
    public void showView(Activity activity, String title, OnMyPickerViewSelectedListener listener) {
        this.activity = new WeakReference<>(activity).get();
        this.title = title;
        this.listener = listener;
        //解析数据完后显示
        parseData();
    }

}
