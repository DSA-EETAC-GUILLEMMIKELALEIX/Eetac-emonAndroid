package com.example.aleix.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.aleix.myapplication.Entity.Capturar;
import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.example.aleix.myapplication.R.id.image;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private GoogleMap mMap;
    final String tag = "MAPACT";
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest = new LocationRequest();
    Location mLastLocation;
    Marker mCurrLocationMarker;
    int eetakemonnormal = 0;
    int eetakemonlegend = 0;
    List<Capturar> captura;

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
        mMap.getUiSettings().setZoomControlsEnabled(false);


            eetakemons();


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
        mMap.clear();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.192.230.97:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
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

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Capturar>> call1 = acta.Eetakname();
        System.out.println("***********DATOS**************************");


        // Fetch and print a list of the contributors to the library.
        call1.enqueue(new Callback<List<Capturar>>() {
            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call<List<Capturar>> call, Response<List<Capturar>> response) {
                captura = (List<Capturar>) response.body();

                Log.d(tag, "Lista size:" + captura.size());

                for(int i=0;i<captura.size();i++){
                    assignarLocalitzacio(captura.get(i).getEetakemon(),captura.get(i).getLatLong().getLatitud(), captura.get(i).getLatLong().getLongitud());
                    Log.d(tag, "Eetakemon: "+captura + "Nombre: "+ captura.get(i).getEetakemon().getNombre().toLowerCase()+"Coord:"+captura.get(i).getLatLong());
                }
                Log.d(tag, "Mostrar Eetakemon correctamente");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR al mostrar");
            }
        });
    }

    Marker m1;

    public void assignarLocalitzacio(final Eetakemon eetakemon, final double lat, final double longi){ //Al fer el get, se li haurà de passar el nom del eetakemon per pritar-lo al mapa

        Log.d(tag, "Loc:"+lat+","+longi + ", "+ eetakemon.getFoto());


        ImageView imageview = new ImageView(MapsActivity.this);


        //{
            final LatLng loc = new LatLng(lat, longi);

            Glide.with(MapsActivity.this)
                .load(eetakemon.getFoto())
                .asBitmap()
                .override(120, 120)
                .into(new BitmapImageViewTarget(imageview){
                    @Override
                    protected void setResource(Bitmap resource){
                        m1 = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .icon(BitmapDescriptorFactory.fromBitmap(resource)));

                        m1.setTag(eetakemon.getId()+"-"+eetakemon.getNombre()+"-"+eetakemon.getTipo());
                        //m1.setTitle(eetakemon.getNombre());
                        Log.d(tag, "bbbbbbb");

                    }
                });
        /*}
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/


        /*imageview.setDrawingCacheEnabled(true);
        imageview.buildDrawingCache();
        Bitmap bmp = imageview.getDrawingCache();*/




        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                Log.d(tag, "Marker "+ arg0.getTag());
                if (arg0.getTag().equals("m1")) Log.d(tag, "Marker "+ arg0.getTag()+ "seleccionat!!");

                Intent intent = new Intent(MapsActivity.this,QuestionActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("nameExtra",arg0.getTitle());
                bundle.putString("objetoExtra",arg0.getTag().toString());
               // bundle.putString("list",captura);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }

        });
    }
/*
    public Bitmap resizeMapIcons(String iconName){
        Log.d(tag, " iconName: "+iconName);
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 150, 150, false);
        Log.d(tag, "AQUIIIIII");
        return resizedBitmap;
    }
*/
}
