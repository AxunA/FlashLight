package com.zyou.flashlight.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by fangzhengyou on 16/1/10.
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
