package com.feicuiedu.treasure.treasure.home.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.feicuiedu.treasure.components.TreasureView;
import com.feicuiedu.treasure.treasure.Treasure;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/6/10 0010. */
public class TreasuresAdapter extends RecyclerView.Adapter<TreasuresAdapter.MyViewHolder> {

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new TreasureView(parent.getContext()));
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        Treasure treasure = datas.get(position);
        holder.treasureView.bindTreasure(treasure);
    }

    @Override public int getItemCount() {
        return datas.size();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {
        final TreasureView treasureView;

        public MyViewHolder(TreasureView itemView) {
            super(itemView);
            treasureView = itemView;
        }
    }

    private ArrayList<Treasure> datas = new ArrayList<Treasure>();

    public final void addItems(Collection<Treasure> items) {
        if (items != null) {
            datas.addAll(items);
            notifyItemRangeChanged(0, items.size());
        }
    }

    @SuppressWarnings("unused")
    public final void deleteItems(Collection<Treasure> items) {
        if (items != null) {
            datas.removeAll(items);
            notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unused")
    public final void clear() {
        datas.clear();
    }
}




