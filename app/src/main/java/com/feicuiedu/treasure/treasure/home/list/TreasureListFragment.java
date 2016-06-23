package com.feicuiedu.treasure.treasure.home.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.treasure.TreasureRepo;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * RecyclerView
 *
 * 1. 用来代替ListView,GridView..的新视图控件
 * 2. 5.0后推出的, V7
 * 3. HolderView封装
 * 4. RecyclerView实现效果,设置你想使用的布局方式    ^
 * 5. 动画                                       ^
 * 6. 对指定位置的刷新
 */
public class TreasureListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TreasuresAdapter adapter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setItemAnimator(new SlideInUpAnimator()); // ItemAnimator
        recyclerView.setBackgroundResource(R.drawable.scale_bg);
        return recyclerView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TreasuresAdapter();
        recyclerView.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                adapter.addItems(TreasureRepo.getInstance().getTreasures());
            }
        },50);
    }
}
