package com.codebrain.minato.tragua;

import android.Manifest;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codebrain.minato.tragua.CustomDialogs.WhereDoYouGo;
import com.codebrain.minato.tragua.CustomDialogs.DialogListener;
import com.codebrain.minato.tragua.CustomDialogs.NewPlaceMarker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends NavigationDrawerBaseActivity implements DialogListener{

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private CameraPosition cameraPosition;

    //the entry point to the places api
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    //Notification Class
    private NotificationWrapper ntWrapper = new NotificationWrapper();

    private boolean mLocationPermissionGranted;//used to known if have permision for location

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnowLocation;
    private LatLng mDefaultLocation = new LatLng(32,80);

    private BottomNavigationView bNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //retrieve saced state
        if (savedInstanceState != null)
        {
            mLastKnowLocation = savedInstanceState.getParcelable(ConstantValues.KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(ConstantValues.KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Navigation View
        bNavigation = findViewById(R.id.bottom_navigation);
        if (bNavigation != null){
            bNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    SelectedFramet(item);
                    return false;
                }
            });
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera. In this case,
             * we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to install
             * it inside the SupportMapFragment. This method will only be triggered once the user has
             * installed Google Play services and returned to the app.
             */
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_place_content,
                                (FrameLayout)findViewById(R.id.map),
                                false);

                        return infoWindow;
                    }
                });

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        FragmentManager fm = getSupportFragmentManager();
                        NewPlaceMarker dialog = new NewPlaceMarker();
                        dialog.show(fm, "New Place MarkerDialog");
                    }
                });

                /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        /*NotificationCompat.Builder mBuilder;
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        int Icono = R.drawable.logo_tragua;
                        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(Icono)
                                .setContentTitle("Notification!")
                                .setContentText("Body of Notification")
                                .setVibrate(new long[] {100,250,100,500})
                                .setAutoCancel(true);

                        notificationManager.notify(1,mBuilder.build());
                        ntWrapper.SetNotification(getApplicationContext(),"Hola");
                    }
                });*/

                getLocationPermission();
                updateLocationUI();
                getDeviceLocation();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        //NewPlaceMarker dialog = new NewPlaceMarker();
        WhereDoYouGo dialog = new WhereDoYouGo();

        dialog.show(fm, "Pruibdusc");
    }

    protected void SelectedFramet(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.nav_camera:

                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (mMap != null)
        {
            outState.putParcelable(ConstantValues.KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(ConstantValues.KEY_LOCATION, mLastKnowLocation);
            super.onSaveInstanceState(outState);
        }
    }

    protected void setCameraPosition(LatLng latLng)
    {
        if (mMap != null)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    private void getDeviceLocation()
    {
        try
        {
            if (mLocationPermissionGranted)
            {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult() == null)
                            {
                                Log.d("Error", "result = null");
                            }
                            else {
                                mLastKnowLocation = task.getResult();
                                setCameraPosition(new LatLng(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude()));
                            }
                        }
                        else
                        {
                            //usig default location
                            setCameraPosition(mDefaultLocation);
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }
        catch (SecurityException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }

    /**
     * Reques permissions
     */
    private void getLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            mLocationPermissionGranted = true;
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ConstantValues.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    protected void addMarker(String title, LatLng position, String data)
    {
        if (mMap != null)
        {
            mMap.addMarker(new MarkerOptions().title(title).position(position).snippet(data));
        }
    }

    /**
     * Manage the response for reques permission
     */
    @Override
    public void onRequestPermissionsResult(int requesCode,
                                         @NonNull String permissions[],
                                         @NonNull int[] grantResults)
    {
        mLocationPermissionGranted = false;
        switch (requesCode)
        {
            case ConstantValues.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    mLocationPermissionGranted = true;
                }
                break;
        }
        updateLocationUI();
    }

    private void updateLocationUI()
    {
        if (mMap == null)   return;
        try
        {
            if (mLocationPermissionGranted)
            {
                Log.d("Location permission", "Granted and enabling button");
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else
            {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnowLocation = null;
                getLocationPermission();
            }
        }
        catch (SecurityException e)
        {
            Log.d("exception", e.getMessage());
        }
    }

    @Override
    public void onCompleteDialog(Bundle args)
    {
        String business = args.getString("business");
        String description = args.getString("description");
        String RUC = args.getString("Cod_Ruc");

        Toast.makeText(getApplicationContext(),"Trabajo: " + business + "Descripcion: " + description + "Ruc: " + RUC,Toast.LENGTH_LONG).show();
    }
}
