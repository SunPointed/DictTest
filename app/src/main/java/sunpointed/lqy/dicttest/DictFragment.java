package sunpointed.lqy.dicttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import sunpointed.lqy.dicttest.Utils.AudioUtils;
import sunpointed.lqy.dicttest.Utils.NetUtils;
import sunpointed.lqy.dicttest.bean.DictItemBean;
import sunpointed.lqy.dicttest.customviews.LoadingView;
import sunpointed.lqy.dicttest.presenter.DictPresenter;
import sunpointed.lqy.dicttest.view.DictView;

/**
 * Created by lqy on 16/5/26.
 */
public class DictFragment extends Fragment implements DictView {

    LoadingView mLvMain;
    RecyclerView mRvMain;
    RvMainAdapter mAdapter;

    SparseArray<DictItemBean> mDictItems;

    DictPresenter mPresenter;

    private float mPrimeY;
    private boolean mCanRefresh;

    private MediaPlayer mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DictPresenter(this);
        mDictItems = new SparseArray<>();
        mCanRefresh = true;
        mPlayer = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_dict, container, false);

        mLvMain = (LoadingView) rootView.findViewById(R.id.lv_main);
        mRvMain = (RecyclerView) rootView.findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRvMain.addItemDecoration(new ListItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        //this is used for refresh at the top or the bottom
        mRvMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                LinearLayoutManager llManger = (LinearLayoutManager) mRvMain.getLayoutManager();
                if (action == MotionEvent.ACTION_DOWN) {
                    if (llManger.findViewByPosition(llManger.findFirstVisibleItemPosition()).getTop() == 0
                            && llManger.findFirstVisibleItemPosition() == 0) {
                        mPrimeY = event.getY();
                    }
                    if (llManger.findViewByPosition(llManger.findLastVisibleItemPosition()).getBottom() == mRvMain.getHeight()) {
                        mPrimeY = event.getY();
                    }
                } else if (action == MotionEvent.ACTION_UP) {

                } else if (action == MotionEvent.ACTION_MOVE) {
                    if (mCanRefresh && llManger.findViewByPosition(llManger.findFirstVisibleItemPosition()).getTop() == 0
                            && llManger.findFirstVisibleItemPosition() == 0
                            && event.getY() - mPrimeY > 200) {
                        mCanRefresh = false;
                        mLvMain.setVisibility(View.VISIBLE);
                        mPresenter.getItemsFromModel();
                    }
                    if (mCanRefresh && llManger.findViewByPosition(llManger.findLastVisibleItemPosition()).getBottom() == mRvMain.getHeight()
                            && mPrimeY - event.getY() > 200) {
                        mCanRefresh = false;
                        mLvMain.setVisibility(View.VISIBLE);
                        mPresenter.getItemsFromModel();
                    }
                }
                return false;
            }
        });

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

    @Override
    public void setRefresh(boolean canRefresh) {
        mCanRefresh = canRefresh;
        mLvMain.setVisibility(View.GONE);
    }

    class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.RvMainViewHolder> {

        Context mContext;
        SparseArray<DictItemBean> mDictItems;

        public void update(SparseArray<DictItemBean> items) {
            mDictItems = items;

            if(mDictItems.size() > 200){
                for(int i=0; i<100; i++){
                    mDictItems.remove(0);
                }
            }

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
        public void onBindViewHolder(RvMainViewHolder holder, final int position) {
            final DictItemBean bean = mDictItems.get(position);

            NetUtils.getImage(bean.subimg, holder.mIvImg);
            holder.mTvEn.setText(bean.suben);
            holder.mTvCn.setText(bean.subcn);

            holder.mTvWord.setText(bean.keyword);
            holder.mTvSymbol.setText(bean.yinbiao);
            holder.mTvFrom.setText(bean.filmname);

            holder.mIvRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPlayer != null){
                        mPlayer.stop();
                    }

                     mPlayer = AudioUtils.createAudioFromNet(bean.wordaudio);
                    if(mPlayer != null) {
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mPlayer.start();
                            }
                        });
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mPlayer.release();
                                mPlayer = null;
                            }
                        });
                        mPlayer.prepareAsync();
                    }
                }
            });
            holder.mTvRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPlayer != null){
                        mPlayer.stop();
                    }


                    mPlayer = AudioUtils.createAudioFromNet(bean.subaudio);
                    if(mPlayer != null) {
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mPlayer.start();
                            }
                        });
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mPlayer.release();
                                mPlayer = null;
                            }
                        });
                        mPlayer.prepareAsync();
                    }
                }
            });
            holder.mTvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 16/5/27
//                    SparseArray<DictItemBean> array = mPresenter.getNewWords(getContext());
//                    for(int i=0; i<array.size(); i++){
//                        Log.i("lqy","---->" + array.get(i).subimg);
//                    }
                }
            });
            holder.mTvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.addNewWord(getContext(), bean);
                }
            });
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
