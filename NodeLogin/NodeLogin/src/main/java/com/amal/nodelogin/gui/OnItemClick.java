package com.amal.nodelogin.gui;

import android.support.v7.widget.RecyclerView;

/**
 * Created by adrian on 23/06/16.
 */
public interface OnItemClick {
    void onItemClick(int position, RecyclerView.ViewHolder viewHolder);

    boolean onItemLongClick(int position, RecyclerView.ViewHolder viewHolder);
}
