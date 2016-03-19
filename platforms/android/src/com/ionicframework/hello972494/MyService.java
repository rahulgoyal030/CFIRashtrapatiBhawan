package com.ionicframework.hello972494;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import network.APICallback;
import network.APIExecutor;

/**
 * Created by Raja on 3/19/2016.
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    String latitude = null;
    String longitude = null;
    String serverResponse = null;
    public static int delay = 60000;
    private Handler mHandler = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return Service.START_STICKY;

    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate: Service Created");
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        LocationListener mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: ");
                Toast.makeText(getApplicationContext(), "Latitude->" + location.getLatitude() +
                        " Longitude->" + location.getLongitude(), Toast.LENGTH_LONG).show();
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Log.d(TAG, "onCreate: Locationlistener " + mLocationListner);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListner);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mLocationListner);
        Log.d(TAG, "onCreate: lcoation manager " + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

        mHandler = new Handler();
        APIExecutor.get(APIExecutor.GET_NEARBY_TOILET + "/" + latitude + "/" + longitude, new APICallback() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    serverResponse = response.getString("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(serverResponse.equals("yes")){


                }

            }

            @Override
            public void onError(JSONObject response) {

            }
        });



    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //TODO cdefine destruction resources releases
    }


}
