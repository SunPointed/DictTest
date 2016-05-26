package sunpointed.lqy.dicttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lqy on 16/5/26.
 */
public class FindFragment extends Fragment implements View.OnClickListener {

    RelativeLayout mRlTranslate;
    RelativeLayout mRlDrama;
    RelativeLayout mRl46;
    RelativeLayout mRlFamous;
    RelativeLayout mRlSchedule;
    RelativeLayout mRlResource;

    public FindFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_find, container, false);

        mRlTranslate = (RelativeLayout) rootView.findViewById(R.id.rl_find_translate);
        mRlTranslate.setOnClickListener(this);
        mRlDrama = (RelativeLayout) rootView.findViewById(R.id.rl_find_drama);
        mRlDrama.setOnClickListener(this);
        mRl46 = (RelativeLayout) rootView.findViewById(R.id.rl_find_46);
        mRl46.setOnClickListener(this);
        mRlFamous = (RelativeLayout) rootView.findViewById(R.id.rl_find_famous);
        mRlFamous.setOnClickListener(this);
        mRlSchedule = (RelativeLayout) rootView.findViewById(R.id.rl_find_schedule);
        mRlSchedule.setOnClickListener(this);
        mRlResource = (RelativeLayout) rootView.findViewById(R.id.rl_find_resouce);
        mRlResource.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_find_translate) {
            // TODO: 16/5/26
        } else if (id == R.id.rl_find_drama) {

        } else if (id == R.id.rl_find_46) {

        } else if (id == R.id.rl_find_famous) {

        } else if (id == R.id.rl_find_schedule) {

        } else if (id == R.id.rl_find_resouce) {

        }
    }
}
