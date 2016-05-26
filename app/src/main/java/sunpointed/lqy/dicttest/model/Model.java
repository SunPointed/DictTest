package sunpointed.lqy.dicttest.model;

import android.util.SparseArray;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import sunpointed.lqy.dicttest.Utils.NetUtils;
import sunpointed.lqy.dicttest.Utils.XmlUtils;
import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.presenter.DictPresenter;

/**
 * Created by lqy on 16/5/26.
 */
public class Model {

    public static final String TAG = "Model";

    SparseArray<DictItemBean> mItems;
    DictPresenter mPresenter;

    public Model(DictPresenter presenter){
        mItems = null;
        mPresenter = presenter;
    }

    public void getDictItems() {

        Call<ResponseBody> call = NetUtils.itemsService.getItems();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
//                    mItems = XmlUtils.fromXml(response.body().string());
                    mItems = XmlUtils.fromXml(data);
                    mPresenter.setItemsToView(mItems);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
