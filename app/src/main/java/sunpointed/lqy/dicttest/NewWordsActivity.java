package sunpointed.lqy.dicttest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.presenter.DictPresenter;
import sunpointed.lqy.dicttest.presenter.NewWordsPresenter;
import sunpointed.lqy.dicttest.view.DictView;
import sunpointed.lqy.dicttest.view.NewWordsView;

/**
 * Created by lqy on 16/5/27.
 */
public class NewWordsActivity extends AppCompatActivity implements NewWordsView {

    NewWordsPresenter mPresenter;

    SparseArray<DictItemBean> mNewWordsItems;

    Toolbar mToolbar;

    ListView mLvNewWords;
    NewWordsAdapter mAdapter;

    private float mPrimeX;
    private float mPrimeY;
    private int mPosition;
    private boolean mIsPositionChanged = false;
    private RelativeLayout mTempRl;
    private Button mTempBtn;
    private ImageView mTempImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_new_words);
        mToolbar.setTitle(R.string.new_words_name);
        mToolbar.setLogo(R.drawable.back);
        setSupportActionBar(mToolbar);

        mLvNewWords = (ListView) findViewById(R.id.lv_new_words);
        mLvNewWords.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                ListView lv = (ListView) v;

                if(action == MotionEvent.ACTION_DOWN){
                    if(mIsPositionChanged) {
                        if(mTempImg != null && mTempBtn != null) {
                            mTempBtn.setVisibility(View.GONE);
                            mTempImg.setVisibility(View.VISIBLE);
                        }
                        mIsPositionChanged = false;
                    }
                    mPrimeX = event.getX();
                    mPrimeY = event.getY();
                } else if(action == MotionEvent.ACTION_MOVE){
                    float dx = Math.abs(event.getX() - mPrimeX);
                    float dy = Math.abs(event.getY() - mPrimeY);
                    mPosition = (int) event.getY();

                    if(!mIsPositionChanged && dx > dy && dx > 100){
                        int itemHeight = lv.getChildAt(0).getMeasuredHeight();
                        mPosition = mPosition/itemHeight;

                        mTempRl = (RelativeLayout) lv.getChildAt(mPosition);
                        mTempBtn = (Button) mTempRl.findViewById(R.id.btn_new_delete);
                        mTempImg = (ImageView) mTempRl.findViewById(R.id.iv_new);

                        mTempBtn.setVisibility(View.VISIBLE);
                        mTempImg.setVisibility(View.GONE);

                        mIsPositionChanged = true;
                    }
                }
                return false;
            }
        });

        mNewWordsItems = new SparseArray<>();
        mAdapter = new NewWordsAdapter(mNewWordsItems, this);
        mLvNewWords.setAdapter(mAdapter);

        mPresenter = new NewWordsPresenter(this);
        mPresenter.setNewWordsToView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_review) {
            // TODO: 16/5/26
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNewWords(SparseArray<DictItemBean> array) {
        mNewWordsItems = array;
        mAdapter.setItems(mNewWordsItems);
    }

    class NewWordsAdapter extends BaseAdapter{

        ArrayList<DictItemBean> mItems = new ArrayList<>();
        LayoutInflater mInflater;


        public NewWordsAdapter(SparseArray<DictItemBean> array, Context context){
            int size = array.size();
            for(int i=0; i<size; i++){
                mItems.add(array.get(i));
            }
//            mItems = array;
            mInflater = LayoutInflater.from(context);
        }

        public void setItems(SparseArray<DictItemBean> array){
            int size = array.size();
            for(int i=0; i<size; i++){
                mItems.add(array.get(i));
            }
//            mItems = array;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems != null ? mItems.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mItems != null ? mItems.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.new_words_item, null);

                holder = new ViewHolder();
                holder.word = (TextView) convertView.findViewById(R.id.tv_new_word);
                holder.explain = (TextView) convertView.findViewById(R.id.tv_new_explain);
                holder.img = (ImageView) convertView.findViewById(R.id.iv_new);
                holder.delete = (Button) convertView.findViewById(R.id.btn_new_delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DictItemBean bean = mItems.get(position);
            holder.word.setText(bean.keyword);
            holder.explain.setText(bean.explain);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.removeWord(position);
                    mItems.remove(position);
                    NewWordsAdapter.this.notifyDataSetChanged();
                }
            });

            return convertView;
        }

        class ViewHolder{
            TextView word;
            TextView explain;
            ImageView img;
            Button delete;
        }
    }
}
