package com.aaron.yqb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aaron.myviews.MyActionBar;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("yqb","hello--------------------");
        setContentView(R.layout.activity_main);
        MyActionBar myActionBar = (MyActionBar) findViewById(R.id.action_bar1);
        myActionBar.setBarBackGround(R.color.colorAccent);
        
    }
}
