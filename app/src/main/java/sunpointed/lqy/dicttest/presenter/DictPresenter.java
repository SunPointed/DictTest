package sunpointed.lqy.dicttest.presenter;

import android.content.Context;
import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.model.DictModel;
import sunpointed.lqy.dicttest.view.DictView;

/**
 * Created by lqy on 16/5/26.
 */
public class DictPresenter {
    private DictModel mModel;
    private DictView mView;

    public DictPresenter(DictView view){
        mView = view;
        mModel = new DictModel(this);
    }

    public void getItemsFromModel(int upOrDown){
        mModel.getDictItems(upOrDown);
    }

    public void setItemsToView(SparseArray<DictItemBean> items){
        mView.setDictItems(items);
        mView.setRefresh(true);
    }

    public void addNewWord(Context context, DictItemBean bean){
        mModel.addNewWord(context, bean);
    }
}
