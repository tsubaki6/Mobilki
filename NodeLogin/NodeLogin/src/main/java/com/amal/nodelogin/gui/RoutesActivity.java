package com.amal.nodelogin.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.amal.nodelogin.R;
import com.amal.nodelogin.model.db.Route;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutesActivity extends AppCompatActivity implements RoutesListFragment.RoutesListInteraction {

    private final String TAG = getClass().getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new RoutesListFragment());
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRouteSelected(Route route) {
        Log.d(getClass().getName(), route.toString());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, RouteDetailsFragment.newInstance(route.getId()))
                .addToBackStack(TAG)
                .commit();
    }
}
