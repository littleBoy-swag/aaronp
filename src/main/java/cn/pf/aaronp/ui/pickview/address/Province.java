package cn.pf.aaronp.ui.pickview.address;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by aaron pan on 2019/5/27.
 */

public class Province implements IPickerViewData {

    public String name;
    public List<City> city;

    public static class City {
        public String name;
        public List<String> area;
    }

    //要返回的省的名字
    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
