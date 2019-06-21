package cn.pf.aaronp.ui.lockview;

/**
 * 存储圆圈的各种属性的实体类
 * Created by aaron pan on 2019/5/31.
 */
public class CircleRect {
    // 圆圈所代表的数字（1-9）
    private int code;
    // 圆心的X坐标
    private int x;
    // 圆心的Y坐标
    private int y;
    // 圆圈的当前状态
    private int state;

    public CircleRect() {
    }

    public CircleRect(int code, int x, int y, int state) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
