package sunpointed.lqy.dicttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    LinearLayout mLlDict;
    LinearLayout mLlFind;
    LinearLayout mLlMine;

    RelativeLayout mRlSearch;

    View mVDict;
    View mVFind;
    View mVMine;
    int mVBackColor = 0xFFF8BBD0;
    int mVClearColor = 0x00FFFFFF;

    DictFragment mDictFragment;
    FindFragment mFindFragment;

    android.support.v4.app.FragmentManager mFManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle("DictTest");
//        mToolbar.setSubtitle("xxx");
//        mToolbar.setLogo(R.drawable.dict);
        setSupportActionBar(mToolbar);

        mLlDict = (LinearLayout) findViewById(R.id.ll_nav_dict);
        mLlDict.setOnClickListener(this);
        mLlFind = (LinearLayout) findViewById(R.id.ll_nav_find);
        mLlFind.setOnClickListener(this);
        mLlMine = (LinearLayout) findViewById(R.id.ll_nav_mine);
        mLlMine.setOnClickListener(this);
        mRlSearch = (RelativeLayout) findViewById(R.id.rl_search);
        mRlSearch.setOnClickListener(this);

        mVDict = findViewById(R.id.v_nav_dict);
        mVDict.setBackgroundColor(mVBackColor);
        mVFind = findViewById(R.id.v_nav_find);
        mVMine = findViewById(R.id.v_nav_mine);

        mDictFragment = new DictFragment();
        mFindFragment = new FindFragment();

        mFManager = getSupportFragmentManager();

        mFManager.beginTransaction().add(R.id.fl_container, mFindFragment).commit();
        mFManager.beginTransaction().hide(mFindFragment).commit();
        mFManager.beginTransaction().add(R.id.fl_container, mDictFragment).commit();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            // TODO: 16/5/26  
        } else if(id == R.id.action_new_words){
            Intent intent = new Intent(MainActivity.this, NewWordsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        setVBackColor(id);
        if(id == R.id.ll_nav_dict){
            mFManager.beginTransaction().show(mDictFragment).commit();
            mFManager.beginTransaction().hide(mFindFragment).commit();
        }else if(id == R.id.ll_nav_find){
            mFManager.beginTransaction().show(mFindFragment).commit();
            mFManager.beginTransaction().hide(mDictFragment).commit();
        }else if(id == R.id.ll_nav_mine){
            // TODO: 16/5/26  
        }else if(id == R.id.rl_search){
            // TODO: 16/5/27
        }
    }

    private void setVBackColor(int id){
        if(id == R.id.ll_nav_dict){
            mVDict.setBackgroundColor(mVBackColor);
            mVFind.setBackgroundColor(mVClearColor);
            mVMine.setBackgroundColor(mVClearColor);
        }else if(id == R.id.ll_nav_find){
            mVDict.setBackgroundColor(mVClearColor);
            mVFind.setBackgroundColor(mVBackColor);
            mVMine.setBackgroundColor(mVClearColor);
        }else if(id == R.id.ll_nav_mine){
            mVDict.setBackgroundColor(mVClearColor);
            mVFind.setBackgroundColor(mVClearColor);
            mVMine.setBackgroundColor(mVBackColor);
        }
    }
}
