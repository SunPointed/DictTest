package sunpointed.lqy.dicttest.presenter;

import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.model.Model;
import sunpointed.lqy.dicttest.view.DictView;

/**
 * Created by lqy on 16/5/26.
 */
public class DictPresenter {
    private Model mModel;
    private DictView mView;

    public DictPresenter(DictView view){
        mView = view;
        mModel = new Model(this);
    }

    public void getItemsFromModel(){
        mModel.getDictItems();
    }

    public void setItemsToView(SparseArray<DictItemBean> items){
        mView.setDictItems(items);
    }
}
