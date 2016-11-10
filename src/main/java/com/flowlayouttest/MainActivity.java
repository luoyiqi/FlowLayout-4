package com.flowlayouttest;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView time ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TextView) findViewById(R.id.text);

        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd  HH:mm:ss");

        Date date = new Date(System.currentTimeMillis());
        String format = sdf.format(date);
        time.setText(format); // 12:00:00  结果： 20:00:00
    }

    public void intentFlowLayout(View view ){
        startActivity(new Intent(this, FlowLayoutTestActivity.class));
    }
}
