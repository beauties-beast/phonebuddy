package com.github.beauties_beast.phonebuddy;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by boggs on 10/10/15.
 */
public class LocationCommand implements CommandInterface {
    private static final String TAG = "LocationCommand";

    @Override
    public boolean parse(String sms, Context context) {
        return action(null, context);
    }

    @Override
    public boolean action(Object options, Context context) {
        Log.d(TAG, String.format("%s action", TAG));
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return true;
    }

    @Override
    public boolean response(Object options, Context context) {
        return false;
    }
}
