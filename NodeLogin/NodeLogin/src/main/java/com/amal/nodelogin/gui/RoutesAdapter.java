package com.amal.nodelogin.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amal.nodelogin.R;
import com.amal.nodelogin.model.db.Route;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adrian on 23/06/16.
 */
public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<Route> elems;
    private OnItemClick onItemClick;

    public RoutesAdapter(List<Route> elems) {
        this.elems = elems;
    }

    public List<Route> elems() {
        return elems;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        RouteViewHolder holder = new RouteViewHolder(view);
        view.setOnClickListener(view1 -> {
            int i = holder.getAdapterPosition();
            if (i != -1 && onItemClick != null) onItemClick.onItemClick(i, holder);
        });
        view.setOnLongClickListener(view1 -> {
            int i = holder.getAdapterPosition();
            if (i != -1 && onItemClick != null) return onItemClick.onItemLongClick(i, holder);
            else return false;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        Route route = elems.get(position);
        holder.name.setText(route.getName());
        holder.distance.setText(route.getDistance() + " m");
    }

    @Override
    public int getItemCount() {
        return elems.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.distance)
        public TextView distance;

        public RouteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
