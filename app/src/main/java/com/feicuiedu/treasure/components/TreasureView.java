package com.feicuiedu.treasure.components;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.treasure.home.Treasure;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/** 宝藏视图*/
public class TreasureView extends RelativeLayout implements View.OnClickListener {

    @Bind(R.id.tv_treasureTitle) TextView tvTitle;

    @Bind(R.id.tv_treasureLocation) TextView tvLocation;

    @Bind(R.id.tv_distance) TextView tvDistance;

    private Treasure treasure;

    public TreasureView(Context context) {
        this(context, null);
    }

    public TreasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_treasure, this, true);
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    // 只要你传入宝藏对象,这里就自动将宝藏数据设置到对应控件上
    public void bindTreasure(Treasure treasure) {
        this.treasure = treasure;
        tvTitle.setText(treasure.getTitle());
        tvLocation.setText(treasure.getLocation());
        double distance = treasure.distanceToMyLocation(); // 计算出宝藏和当前位置的距离
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        String text = decimalFormat.format(distance / 1000) + "km";
        tvDistance.setText(text);
    }

    @Override
    public void onClick(View v) {
    }
}
