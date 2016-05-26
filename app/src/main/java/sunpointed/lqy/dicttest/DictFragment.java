package sunpointed.lqy.dicttest;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sunpointed.lqy.dicttest.Utils.NetUtils;
import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.presenter.DictPresenter;
import sunpointed.lqy.dicttest.view.DictView;

/**
 * Created by lqy on 16/5/26.
 */
public class DictFragment extends Fragment implements DictView {

    RecyclerView mRvMain;
    RvMainAdapter mAdapter;

    SparseArray<DictItemBean> mDictItems;

    DictPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DictPresenter(this);
        mDictItems = new SparseArray<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_dict, container, false);

        mRvMain = (RecyclerView) rootView.findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRvMain.addItemDecoration(new ListItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mAdapter = new RvMainAdapter(getContext(), mDictItems);
        mRvMain.setAdapter(mAdapter);

        mPresenter.getItemsFromModel();

        return rootView;
    }

    @Override
    public SparseArray<DictItemBean> getDictItems() {
        return mDictItems;
    }

    @Override
    public void setDictItems(SparseArray<DictItemBean> items) {

        if (items != null) {
            mAdapter.update(items);
        }
    }

    class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.RvMainViewHolder> {

        Context mContext;
        SparseArray<DictItemBean> mDictItems;

        public void update(SparseArray<DictItemBean> items){
            mDictItems = items;
            notifyDataSetChanged();
        }

        public RvMainAdapter(Context context, SparseArray<DictItemBean> items) {
            super();
            mContext = context;
            mDictItems = items;
        }

        @Override
        public RvMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            RvMainViewHolder holder = new RvMainViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.dict_item, parent,
                    false));

            return holder;

        }

        @Override
        public void onBindViewHolder(RvMainViewHolder holder, int position) {
            DictItemBean bean = mDictItems.get(position);

//            holder.mIvImg;
            NetUtils.getImage(bean.subimg, holder.mIvImg);
            holder.mTvEn.setText(bean.suben);
            holder.mTvCn.setText(bean.subcn);

            holder.mTvWord.setText(bean.keyword);
            holder.mTvSymbol.setText(bean.yinbiao);
            holder.mTvFrom.setText(bean.filmname);
        }

        @Override
        public int getItemCount() {
            return mDictItems.size();
        }

        class RvMainViewHolder extends RecyclerView.ViewHolder {

            ImageView mIvImg;
            TextView mTvEn;
            TextView mTvCn;

            TextView mTvWord;
            TextView mTvSymbol;

            TextView mTvFrom;
            TextView mTv46;
            ImageView mIvRead;

            TextView mTvRead;
            TextView mTvView;
            TextView mTvAdd;

            public RvMainViewHolder(View itemView) {
                super(itemView);
                mIvImg = (ImageView) itemView.findViewById(R.id.iv_dict_img);
                mTvEn = (TextView) itemView.findViewById(R.id.tv_dict_en);
                mTvCn = (TextView) itemView.findViewById(R.id.tv_dict_cn);

                mTvWord = (TextView) itemView.findViewById(R.id.tv_dict_word);
                mTvSymbol = (TextView) itemView.findViewById(R.id.tv_dict_symbol);
                Typeface face= Typeface.createFromAsset(getContext().getAssets(), "fonts/DroidSans.ttf");
                mTvSymbol.setTypeface(face);

                mTvFrom = (TextView) itemView.findViewById(R.id.tv_dict_from);
                mTv46 = (TextView) itemView.findViewById(R.id.tv_dict_46);
                mIvRead = (ImageView) itemView.findViewById(R.id.iv_dict_read);

                mTvRead = (TextView) itemView.findViewById(R.id.tv_dict_read);
                mTvView = (TextView) itemView.findViewById(R.id.tv_dict_view);
                mTvAdd = (TextView) itemView.findViewById(R.id.tv_dict_add_new);
            }
        }
    }
}
