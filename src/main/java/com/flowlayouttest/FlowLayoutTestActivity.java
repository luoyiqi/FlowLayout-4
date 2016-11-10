package com.flowlayouttest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.MarginLayoutParams;

/**
 * Created by HaoPz_PC on 2016/11/1.
 */

public class FlowLayoutTestActivity extends AppCompatActivity {

    private String mNames[] = {
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat"
    };

    private FlowLayout mFlowLayout ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        initChildViews();
    }

    private void initChildViews() {
        mFlowLayout = (FlowLayout) findViewById(R.id.mFlowLayout);
        MarginLayoutParams lp = new MarginLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;

        for(int i=0 ; i<mNames.length ; i++){
            TextView textview = new TextView(this);
            textview.setText(mNames[i]);
            textview.setTextColor(Color.WHITE);
            textview.setPadding(15,30,15,30);
            final int finalI = i;
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("*********************", "-----------------------"+ finalI);
                }
            });
            textview.setBackground(getResources().getDrawable(R.drawable.shape_flow));
            mFlowLayout.addView(textview, lp);
        }
    }
}
