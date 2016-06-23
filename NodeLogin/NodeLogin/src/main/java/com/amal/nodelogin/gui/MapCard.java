package com.amal.nodelogin.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.amal.nodelogin.R;
import com.amal.nodelogin.model.db.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by adrian on 23/06/16.
 */
public class MapCard extends Card implements OnMapReadyCallback {

    private final Route route;
    @BindView(R.id.map)
    MapView map;

    public MapCard(Context context, Route route) {
        super(context, R.layout.card_inner);
        this.route = route;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        ButterKnife.bind(this, view);
        map.onCreate(null);
        map.getMapAsync(this);
        map.setClickable(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        LatLng[] points = route.getPoints();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        PolylineOptions polylineOptions = new PolylineOptions()
                .width(12)
                .color(Color.parseColor("#05b1fb"))
                .geodesic(true);
        for (LatLng point : points) {
            builder.include(point);
            polylineOptions.add(point);
        }
        googleMap.addPolyline(polylineOptions);
        googleMap.setOnMapLoadedCallback(() -> {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), dpToPx(6)));
        });
        map.onResume();
    }

    static public int dpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }
}
