package sunpointed.lqy.dicttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lqy on 16/5/28.
 */
public class DictItemContentActivity extends AppCompatActivity {

    public static final String SUBID = "SUB_ID";

    Toolbar mToolbar;
    WebView mWebView;

    String mUrl = "http://www.91dict.com/rr/subcontext2.php?id=";
    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_item_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_dict_item);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DictItemContentActivity.this.finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.wv_dict_item);

        Intent intent = getIntent();
        mId = intent.getStringExtra(SUBID);

        if (mId != null) {
            mWebView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            mWebView.loadUrl(mUrl + mId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dict_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dict_item_text) {
            // TODO: 16/5/28
        }

        return super.onOptionsItemSelected(item);
    }
}
