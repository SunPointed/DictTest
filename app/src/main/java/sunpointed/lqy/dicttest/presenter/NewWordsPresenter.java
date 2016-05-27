package sunpointed.lqy.dicttest.presenter;

import android.content.Context;
import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.model.NewWordsModel;
import sunpointed.lqy.dicttest.view.NewWordsView;

/**
 * Created by lqy on 16/5/27.
 */
public class NewWordsPresenter {
    private NewWordsModel mModel;
    private NewWordsView mView;

    public NewWordsPresenter(NewWordsView view){
        mView = view;
        mModel = new NewWordsModel(this);
    }

    public void setNewWordsToView(Context context){
        mView.setNewWords(mModel.getNewWords(context));
    }
}
