package com.amal.nodelogin.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.amal.nodelogin.R;
import com.amal.nodelogin.model.db.Route;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adrian on 23/06/16.
 */
public class RoutesListFragment extends Fragment implements OnItemClick {

    @BindView(R.id.list)
    RecyclerView list;
    RoutesAdapter adapter;
    RoutesListInteraction listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_list, container, false);
        ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RoutesAdapter(new Select().from(Route.class).execute());
        list.setAdapter(adapter);
        adapter.setOnItemClick(this);
        return view;
    }

    @Override
    public void onItemClick(int position, RecyclerView.ViewHolder viewHolder) {
        if (listener != null) listener.onRouteSelected(adapter.elems().get(position));
    }

    @Override
    public boolean onItemLongClick(int position, RecyclerView.ViewHolder viewHolder) {
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (RoutesListInteraction) activity;
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface RoutesListInteraction {
        void onRouteSelected(Route route);
    }
}