package sunpointed.lqy.dicttest.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.util.SparseArray;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import sunpointed.lqy.dicttest.Utils.NetUtils;
import sunpointed.lqy.dicttest.Utils.XmlUtils;
import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.database.DictStoreHelper;
import sunpointed.lqy.dicttest.presenter.DictPresenter;

/**
 * Created by lqy on 16/5/26.
 */
public class DictModel {

    public static final String TAG = "DictModel";
    public static final int UP = 0;
    public static final int DOWN = 1;

    SparseArray<DictItemBean> mItems;
    DictStoreHelper mHelper;
    DictPresenter mPresenter;
    int mUpOrDown;

//    private long mIndexOfNewWord;

    public DictModel(DictPresenter presenter) {
        mItems = null;
        mHelper = null;
//        mIndexOfNewWord = -1;
        mPresenter = presenter;
    }

    public void getDictItems(int upOrDown) {
        mUpOrDown = upOrDown;
        Call<ResponseBody> call = NetUtils.itemsService.getItems();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    if (response.body() == null) {
                        return;
                    }
                    String data = response.body().string();
                    SparseArray<DictItemBean> temp = XmlUtils.fromXml(data);
                    if (mItems == null) {
                        mItems = temp;
                    } else {
                        if(mUpOrDown == UP){
                            mItems = temp;
                        } else {
                            for (int i = 0; i < temp.size(); i++) {
                                mItems.append(mItems.size(), temp.get(i));
                            }
                        }
                    }
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

    public void addNewWord(Context context, DictItemBean bean) {
        if (mHelper == null) {
            mHelper = new DictStoreHelper(context.getApplicationContext(), "dict.db", null, 1);
        }
        SQLiteDatabase database = mHelper.getWritableDatabase();

//        if (mIndexOfNewWord == -1) {
//            SQLiteStatement s = database.compileStatement("select count(*) from " + DictStoreHelper.TABLE_NAME);
//            mIndexOfNewWord = s.simpleQueryForLong();
//            Log.i("lqy","mIndexOfNewWord------>" + mIndexOfNewWord);
//        }

        ContentValues values = new ContentValues();
        values.put(DictStoreHelper.SUB_IMG, bean.subimg);
        values.put(DictStoreHelper.SUB_EN, bean.suben);
        values.put(DictStoreHelper.SUB_CN, bean.subcn);
        values.put(DictStoreHelper.KEY_WORD, bean.keyword);
        values.put(DictStoreHelper.YIN_BIAO, bean.yinbiao);
        values.put(DictStoreHelper.EXPLAIN, bean.explain);
        values.put(DictStoreHelper.SUB_AUDIO, bean.subaudio);
        values.put(DictStoreHelper.WORD_AUDIO, bean.wordaudio);
        values.put(DictStoreHelper.SUB_ID, bean.subid);
        values.put(DictStoreHelper.S_ID, bean.sid);
        values.put(DictStoreHelper.FILM_NAME, bean.filmname);
        values.put(DictStoreHelper.FILM_ID, bean.filmid);
        database.insert(DictStoreHelper.TABLE_NAME, DictStoreHelper.ID, values);
//        mIndexOfNewWord++;

    }
}
