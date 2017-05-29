package com.example.aleix.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private GoogleMap mMap;
    final String tag = "MAPACT";
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest = new LocationRequest();
    Location mLastLocation;
    Marker mCurrLocationMarker;
    int eetakemonnormal = 0;
    int eetakemonlegend = 0;

    private Button actbttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used wfdgws.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();

        /*mMap.setOnMarkerClickListener(new OnMarkerClickListener()
                                      {

                                          @Override
                                          public boolean onMarkerClick(Marker arg0) {
                                              if(arg0.getTitle().equals("MyHome")) // if marker source is clicked
                                                  Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                                              return true;
                                          });
*/
        Button Menu = (Button) findViewById(R.id.Menu);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }


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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


            eetakemons();

        /*Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                eetakemons(); //Probar funcio simple
            }
        }, 0, 300000);*/
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setMyLocationEnabled();
        startLocationUpdates();
    }
    protected void startLocationUpdates() {
        if (checkLocationPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f));
    }


    private void setMyLocationEnabled() {
        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (getMyLatLng() != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLatLng(), 19f));
            }
            Log.i(tag, "My Location enabled");
        }
    }

    private LatLng getMyLatLng() {
        if (checkLocationPermission()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
        return null;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);
                            if (getMyLatLng() != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLatLng(), 19f));
                            }
                            Log.i(tag, "My Location enabled");
                        }

                    } else {
                        Log.i(tag, "" + ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION));
                        Log.i(tag, "" + PackageManager.PERMISSION_GRANTED);
                        Log.e(tag, "My Location Errors");
                    }
                }
            }
        }
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private void eetakemons(){
        String eetakemon = "bernorlax";
        assignarLocalitzacio(eetakemon);
        int i;

        //3 eetakemons nivell inferior
        for(i=0; i<3; i++){
            actbttn = (Button) findViewById(R.id.ACT);

            actbttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tipo="Inferior";
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
                            .addConverterFactory(GsonConverterFactory.create());
//
                    Retrofit retrofit =
                            builder
                                    .client(
                                            httpClient.build()
                                    )
                                    .build();

                    // Create an instance of our GitHub API interface.
                    Service acta = retrofit.create(Service.class);
                    Eetakemon eetakemonn = new Eetakemon(tipo);

                    // Create a call instance for looking up Retrofit contributors.
                    Call<Eetakemon> call = acta.Eetak(eetakemonn);
                    System.out.println("***********DATOS**************************");


                    // Fetch and print a list of the contributors to the library.
                    call.enqueue(new Callback() {


                        //***************Comprobacion de que recoge los datos**********
                        @Override
                        public void onResponse(Call call, Response response) {
                            Eetakemon e = new Eetakemon();
                            assignarLocalitzacio(e.getNombre());
                            Log.d(tag, "Mostrar Eetakemon correctamente");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(tag, "ERROR al mostrar");
                        }
                    });


                }
            });
        }
        /*
        //1 eetakemon nivell normal cada 20 min
        tipo="Normal";
        //eetakemonnormal++;
        Service aaa =Service.retrofit.create(Service.class);
        final Call<Eetakemon> call = aaa.eetak(tipo);
        e = call.execute().body();
        assignarLocalitzacio(e.getNombre());
        if (eetakemonnormal % 4 == 0){
            //Fer un GET i que et retorni el nom de un eetakemon de nivell normal

            assignarLocalitzacio(eetakemon);
        }

        //1 eetakemon nivell normal cada 1 hora
        tipo="Legendario";
        //eetakemonlegend++;
        Service bbb =Service.retrofit.create(Service.class);
        final Call<Eetakemon> call2 = bbb.eetak(tipo);
        e = call2.execute().body();
        assignarLocalitzacio(e.getNombre());
        if (eetakemonlegend % 12 == 0){
            tipo="Inferior";
            //Fer un GET i que et retorni el nom de un eetakemon de nivell legendari

            assignarLocalitzacio(eetakemon);
        }*/
    }

    public void assignarLocalitzacio(String eetakemon){ //Al fer el get, se li haurà de passar el nom del eetakemon per pritar-lo al mapa

        LatLng aa = new LatLng(41.27539318720677, 1.9851908683449437); //Biblioteca
        LatLng bb = new LatLng(41.274566700768275, 1.9851908683449437);//Residencia
        LatLng cc = new LatLng(41.275224665468244, 1.986177653066079); //Entrada EETAC-1
        LatLng dd = new LatLng(41.275561305325134, 1.9871539771884272);//Entrada EETAC-2
        LatLng ee = new LatLng(41.27564395319903, 1.9865638912051509); //Entrada ESAB
        LatLng ff = new LatLng(41.27557178752102, 1.9858227968461506); //Canasta Basquet
        LatLng gg = new LatLng(41.275515250643224, 1.9840220282936217); //Pont
        LatLng hh = new LatLng(41.27581166751877, 1.9877861738041247); //Edifici professors
        LatLng ii = new LatLng(41.27523514765666, 1.9881053566871287); //Entrada UOC
        LatLng jj = new LatLng(41.275731035685105, 1.989977538569292); //Parking

        Random rand = new Random();
        int n = rand.nextInt(9);

        if (n==0){
            mMap.addMarker(new MarkerOptions()
                    .position(aa)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }
        if (n==1){
            mMap.addMarker(new MarkerOptions()
                    .position(bb)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==2){
            mMap.addMarker(new MarkerOptions()
                    .position(cc)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==3){
            mMap.addMarker(new MarkerOptions()
                    .position(dd)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==4){
            mMap.addMarker(new MarkerOptions()
                    .position(ee)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==5){
            mMap.addMarker(new MarkerOptions()
                    .position(ff)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==6){
            mMap.addMarker(new MarkerOptions()
                    .position(gg)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==7){
            mMap.addMarker(new MarkerOptions()
                    .position(hh)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==8){
            mMap.addMarker(new MarkerOptions()
                    .position(ii)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }if (n==9){
            mMap.addMarker(new MarkerOptions()
                    .position(jj)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(eetakemon,150,150))));
        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
