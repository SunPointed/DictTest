package sunpointed.lqy.dicttest.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.database.DictStoreHelper;
import sunpointed.lqy.dicttest.presenter.NewWordsPresenter;

/**
 * Created by lqy on 16/5/27.
 */
public class NewWordsModel {

    DictStoreHelper mHelper;
    NewWordsPresenter mPresenter;

    public NewWordsModel(NewWordsPresenter presenter){
        mHelper = null;
        mPresenter = presenter;
    }

    public SparseArray<DictItemBean> getNewWords(Context context) {
        SparseArray<DictItemBean> array = new SparseArray<>();
        if (mHelper == null) {
            mHelper = new DictStoreHelper(context.getApplicationContext(), "dict.db", null, 1);
        }
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = database.query(DictStoreHelper.TABLE_NAME, null, null, null, null, null, null);

        boolean isExist = cursor.moveToFirst();
        while (isExist){
            DictItemBean bean = new DictItemBean();
            bean.subimg = cursor.getString(cursor.getColumnIndex(DictStoreHelper.SUB_IMG));
            bean.suben = cursor.getString(cursor.getColumnIndex(DictStoreHelper.SUB_EN));
            bean.subcn = cursor.getString(cursor.getColumnIndex(DictStoreHelper.SUB_CN));
            bean.keyword = cursor.getString(cursor.getColumnIndex(DictStoreHelper.KEY_WORD));
            bean.yinbiao = cursor.getString(cursor.getColumnIndex(DictStoreHelper.YIN_BIAO));
            bean.explain = cursor.getString(cursor.getColumnIndex(DictStoreHelper.EXPLAIN));
            bean.subaudio = cursor.getString(cursor.getColumnIndex(DictStoreHelper.SUB_AUDIO));
            bean.wordaudio = cursor.getString(cursor.getColumnIndex(DictStoreHelper.WORD_AUDIO));
            bean.subid = cursor.getInt(cursor.getColumnIndex(DictStoreHelper.SUB_ID));
            bean.sid = cursor.getInt(cursor.getColumnIndex(DictStoreHelper.S_ID));
            bean.filmname = cursor.getString(cursor.getColumnIndex(DictStoreHelper.FILM_NAME));
            bean.filmid = cursor.getString(cursor.getColumnIndex(DictStoreHelper.FILM_ID));
            array.append(array.size(), bean);

            isExist = cursor.moveToNext();
        }

        cursor.close();
        database.close();

        return array;
    }

    public void removeWord(Context context, String word) {
        if (mHelper == null) {
            mHelper = new DictStoreHelper(context.getApplicationContext(), "dict.db", null, 1);
        }
        SQLiteDatabase database = mHelper.getWritableDatabase();

        Log.i("lqy","word--->" + word);
        String args[] = {word};
        database.delete(DictStoreHelper.TABLE_NAME, DictStoreHelper.KEY_WORD + "=?", args);
        database.close();
    }
}
