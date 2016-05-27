package sunpointed.lqy.dicttest.view;

import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;

/**
 * Created by lqy on 16/5/27.
 */
public interface NewWordsView {
    void setNewWords(SparseArray<DictItemBean> array);
}
