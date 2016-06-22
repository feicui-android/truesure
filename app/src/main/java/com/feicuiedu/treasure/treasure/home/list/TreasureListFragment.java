package com.feicuiedu.treasure.treasure.home.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure.R;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class TreasureListFragment extends Fragment {

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setBackgroundResource(R.drawable.scale_bg);
        // TreasureRepo.getInstance().getTreasures();
        return recyclerView;
    }
}
