package com.amal.nodelogin.gui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amal.nodelogin.App;
import com.amal.nodelogin.R;
import com.amal.nodelogin.model.route.GoogleRoutesResponse;
import com.amal.nodelogin.model.route.Route;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int ASK_LOC = 1;
    private ArrayList<LatLng> latlng = new ArrayList<LatLng>();
    private Route route;
    private String apiKey;
    ;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest = createLocationRequest();
    private Subscription subscription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.add_route)
    FloatingActionButton addRouteButton;
    @BindView(R.id.cancel_route)
    FloatingActionButton cancelRouteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        apiKey = getString(R.string.google_api_key);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpMapIfNeeded();
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    private void setUpMap() //If the setUpMapIfNeeded(); is needed then...
    {
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ASK_LOC);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_LOC: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
                } else {
                    Toast.makeText(this, "Give me permission!!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        Log.d("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]");
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude, point.longitude)));
        latlng.add(point);
        if (latlng.size() <= 1) return;//One point is not enough to draw a polyline
        addRouteButton.show();
        cancelRouteButton.show();

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latlng.get(0).latitude + "," + latlng.get(0).longitude + "&destination=" + latlng.get(1).latitude + "," + latlng.get(1).longitude + "&key=" + apiKey + "&mode=walking";
        if (latlng.size() > 2) {
            Log.d("DEBUG2", "Map clicked [" + latlng.get(2).longitude + "]");
            url = url + "&waypoints=";
            int i;
            for (i = 2; i < latlng.size(); i++) {
                url = url + latlng.get(i).latitude + "," + latlng.get(i).longitude + "|";
            }
            url = url.substring(0, url.length() - 1);
            Log.d("DEBUG3", url);
        }
        if (subscription != null) subscription.unsubscribe();
        subscription = App.get().getRouteApi().route(url)
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(this::handleRouteApiResponse);
    }

    public void handleRouteApiResponse(GoogleRoutesResponse resp) {
        if (resp.getRoutes().isEmpty()) return;
        route = resp.getRoutes().get(0);
        String encodedPolyline = route.getOverviewPolyline().getPoints();

        List<LatLng> list = decodePoly(encodedPolyline);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .addAll(list)
                .width(12)
                .color(Color.parseColor("#05b1fb"))//Google maps blue color
                .geodesic(true)
        );
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (isGoogleMapsInstalled()) {
                if (mMap != null) {
                    setUpMap();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Install Google Maps");
                builder.setCancelable(false);
                builder.setPositiveButton("Install", getGoogleMapsListener());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public DialogInterface.OnClickListener getGoogleMapsListener() {
        return (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
            startActivity(intent);
            finish();
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        if (client != null) {
            client.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (client != null) {
            client.disconnect();
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ASK_LOC);
            return;
        } else
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, R.string.play_services_connect_err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (itemId == R.id.routes) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.add_route)
    public void onAddRouteClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_add_route_title)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.dialog_add_route_name_hint, 0, this::handleAddRoute).show();
    }

    public void handleAddRoute(MaterialDialog dialog, CharSequence name) {
        if (latlng.size() <= 1) {
            Snackbar.make(toolbar, "Add more points", Snackbar.LENGTH_SHORT).show();
            return;
        }
        com.amal.nodelogin.model.db.Route.from(route, latlng, name.toString()).save();
        latlng.clear();
        mMap.clear();
        addRouteButton.hide();
        cancelRouteButton.hide();
        Snackbar.make(toolbar, "Saved", Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.cancel_route)
    public void onCancelRouteClick() {
        latlng.clear();
        mMap.clear();
        addRouteButton.hide();
        cancelRouteButton.hide();
    }
}