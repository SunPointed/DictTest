package sunpointed.lqy.dicttest.view;

import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;

/**
 * Created by lqy on 16/5/26.
 */
public interface DictView {
    SparseArray<DictItemBean> getDictItems();
    void setDictItems(SparseArray<DictItemBean> items);
    void setRefresh(boolean canRefresh);
}
