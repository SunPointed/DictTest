package sunpointed.lqy.dicttest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lqy on 16/5/27.
 */
public class DictStoreHelper extends SQLiteOpenHelper {

    public String subimg;
    public String suben;
    public String subcn;

    public String keyword;
    public String yinbiao;
    public String explain;

    public String subaudio;
    public String wordaudio;
    public int subid;
    public int sid;

    public String filmname;
    public String filmid;

    public static final String TABLE_NAME = "new_words";
    public static final String ID = "id";
    public static final String SUB_IMG = "subimg";
    public static final String SUB_EN = "suben";
    public static final String SUB_CN = "subcn";
    public static final String KEY_WORD = "keyword";
    public static final String YIN_BIAO = "yinbiao";
    public static final String EXPLAIN = "explain";
    public static final String SUB_AUDIO = "subaudio";
    public static final String WORD_AUDIO = "wordaudio";
    public static final String SUB_ID = "subid";
    public static final String S_ID = "sid";
    public static final String FILM_NAME = "filmname";
    public static final String FILM_ID = "filmid";
    public static final String DICT_TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " integer primary key, " +
                    SUB_IMG + " varchar, " +
                    SUB_EN + " varchar, " +
                    SUB_CN + " varchar, " +
                    KEY_WORD + " varchar unique, " +
                    YIN_BIAO + " varchar, " +
                    EXPLAIN + " varchar, " +
                    SUB_AUDIO + " varchar, " +
                    WORD_AUDIO + " varchar, " +
                    SUB_ID + " varchar, " +
                    S_ID + " varchar, " +
                    FILM_NAME + " varchar, " +
                    FILM_ID + " varchar) ";

    public DictStoreHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
