package com.zyou.flashlight.ui.activity;

import android.os.Bundle;

import com.zyou.flashlight.R;

/**
 * Created by fangzhengyou on 16/1/10.
 */
public class SuggestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        setTitle(R.string.suggestion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
