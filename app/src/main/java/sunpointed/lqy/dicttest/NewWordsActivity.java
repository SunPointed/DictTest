package sunpointed.lqy.dicttest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_new_words);
        mToolbar.setTitle(R.string.new_words_name);
        mToolbar.setLogo(R.drawable.back);
        setSupportActionBar(mToolbar);

        mLvNewWords = (ListView) findViewById(R.id.lv_new_words);
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

        SparseArray<DictItemBean> mItems;
        LayoutInflater mInflater;


        public NewWordsAdapter(SparseArray<DictItemBean> array, Context context){
            mItems = array;
            mInflater = LayoutInflater.from(context);
        }

        public void setItems(SparseArray<DictItemBean> array){
            mItems = array;
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
        public View getView(int position, View convertView, ViewGroup parent) {
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
                    // TODO: 16/5/27
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
