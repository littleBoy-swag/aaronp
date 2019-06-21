package cn.pf.aaronp.base;

/**
 * Created by aaron pan on 2019/5/24.
 */

public abstract class BasePresenter<M, V> {
    public M mIModel;
    public V mIView;

    /**
     * 绑定
     * @param v
     */
    public void attachView(V v) {
        this.mIView = v;
        if (mIModel == null) {
            mIModel = createModel();
        }
    }

    /**
     * 当View被销毁掉时删除Presenter层对View层的引用
     */
    public void detachView() {
        mIView = null;
    }

    protected abstract M createModel();

}
