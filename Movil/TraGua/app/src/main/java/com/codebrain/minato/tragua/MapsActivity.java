package com.codebrain.minato.tragua;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.codebrain.minato.tragua.CustomDialogs.DialogListener;
import com.codebrain.minato.tragua.CustomDialogs.PlaceInfoDialog;
import com.codebrain.minato.tragua.CustomDialogs.WhereDoYouGo;

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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

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

    Marker lastLocationClicked = null, markerOrigin = null, markerDestiny = null;
    Polyline polyline = null;

    //Test Circle
    LatLng center = new LatLng(12.1493312,-86.2730808);
    int radius = 500;

    float [] resultado = new float[2];
    double latitude = center.latitude;
    double longitude = center.longitude;

    //Location
    LocationManager locationManager;
    double latAct;
    double lonAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        /*if (bNavigation != null){
            bNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    switch (item.getItemId()){
                        case R.id.nav_camera:

                            break;
                        case R.id.nav_gallery:
                            UpdatePosition();
                            Toast.makeText(getApplicationContext(),"hd",Toast.LENGTH_LONG).show();
                            break;
                        case R.id.nav_slideshow:

                            break;
                        case R.id.nav_manage:

                            break;
                        case R.id.nav_share:

                            break;
                    }
                    return false;
                }
            });
        }*/

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

                /*mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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
                });*/

               /* mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        FragmentManager fm = getSupportFragmentManager();
                        NewPlaceMarker dialog = new NewPlaceMarker();
                        dialog.show(fm, "New Place MarkerDialog");
                    }
                });*/

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (markerOrigin != null)markerOrigin.remove();
                        if (markerDestiny != null)markerDestiny.remove();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PlaceInfoDialog dialog = new PlaceInfoDialog();
                        dialog.show(fragmentManager, "PlaceInfo");
                        //marker.showInfoWindow();;
                        //String tag = marker.getTag().toString();

//                        if (tag != null)
//                        {
//                            Toast.makeText(getApplicationContext(), "Message: " + tag, Toast.LENGTH_LONG).show();
//                        }
                        return false;
                    }
                });

                mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                    @Override
                    public void onPoiClick(PointOfInterest pointOfInterest) {
                        if (lastLocationClicked != null)
                        {
                            lastLocationClicked.remove();
                        }
                        Toast.makeText(getApplicationContext(), "Data: " + pointOfInterest.name + " id " + pointOfInterest.placeId, Toast.LENGTH_LONG).show();
                        lastLocationClicked = addMarker("pruea1", pointOfInterest.latLng, "Hello");
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
    }

    private void UpdatePosition() {
        CircleOptions circleOptions = new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeColor(Color.parseColor("#0D47A1"))
                .strokeWidth(4)
                .fillColor(Color.argb(32, 33, 150, 243));
        Circle circle = mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 17));

        Location.distanceBetween(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude(),
                circle.getCenter().latitude,
                circle.getCenter().longitude,
                resultado);

        if (resultado[0] > circle.getRadius()) {
            Toast.makeText(getApplicationContext(), "Estas Dentro", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Estas Fuera", Toast.LENGTH_LONG).show();
        }
    }

    protected void SelectedFramet(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.nav_Salud:
                Toast.makeText(getApplicationContext(),"Cargando Lugares de Salud",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_Tienda:
                Toast.makeText(getApplicationContext(),"Cargando Lugares de Tienda",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_publicos:
                Toast.makeText(getApplicationContext(),"Cargando Lugares de Publicos",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_Buffes:
                Toast.makeText(getApplicationContext(),"Cargando Lugares de Buffes",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_Discoteca:
                Toast.makeText(getApplicationContext(),"Cargando Lugares de Discoteca",Toast.LENGTH_LONG).show();
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

    protected Marker addMarker(String title, LatLng position, String data)
    {
        if (mMap != null)
        {
            return mMap.addMarker(new MarkerOptions().title(title).position(position).snippet(data));
        }
        return null;
    }

    //udat

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
        switch (args.getInt("dialog"))
        {
            case 1:
                //where do you go
                final LatLng latLngOrigin = new LatLng(args.getDouble("lat1"), args.getDouble("lon1"));
                final LatLng latLngDestiny = new LatLng(args.getDouble("lat2"), args.getDouble("lon2"));

                GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_key)).
                        from(latLngOrigin)
                        .to(latLngDestiny)
                        .transportMode(TransportMode.DRIVING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if (direction.isOK())
                                {
                                    Route route = direction.getRouteList().get(0);

                                    mMap.addMarker(new MarkerOptions().position(latLngOrigin));
                                    mMap.addMarker(new MarkerOptions().position(latLngDestiny));

                                    ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                                    mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED));

                                    LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
                                    LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
                                    LatLngBounds bounds = new LatLngBounds(southwest, northeast);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {

                            }
                        });
                break;
            case 2:
                //New Place marker dialog
                String business = args.getString("business");
                String description = args.getString("description");
                String RUC = args.getString("Cod_Ruc");

                Toast.makeText(getApplicationContext(),"Trabajo: " + business + "Descripcion: " + description + "Ruc: " + RUC,Toast.LENGTH_LONG).show();
                break;
            case 10:
                //trazaar ruta
                final LatLng latLngOrig = new LatLng(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude());
                final LatLng latLngDesti = lastLocationClicked.getPosition();

                GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_key)).
                        from(latLngOrig)
                        .to(latLngDesti)
                        .transportMode(TransportMode.DRIVING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if (direction.isOK())
                                {
                                    Route route = direction.getRouteList().get(0);
                                    if (lastLocationClicked != null)lastLocationClicked.remove();

                                    markerOrigin = mMap.addMarker(new MarkerOptions().position(latLngOrig));
                                    markerDestiny = mMap.addMarker(new MarkerOptions().position(latLngDesti));
                                    if (polyline != null)
                                        polyline.remove();

                                    ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                                    polyline = mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED));

                                    LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
                                    LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
                                    LatLngBounds bounds = new LatLngBounds(southwest, northeast);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {

                            }
                        });
                break;
        }
    }

   /* private boolean isLocationEnabled(){
        return locationManager.isProviderEnabled(locationManager.GPS_PROVIDER ||
                locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER));
    }*/
}
