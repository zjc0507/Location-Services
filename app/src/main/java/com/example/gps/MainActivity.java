package com.example.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    public Button button;
    public TextView textView;
    public TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR);

        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest req = new LocationRequest();
        req.setInterval(5000); // 2 seconds
        req.setFastestInterval(500); // 500 milliseconds
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                       new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        404);



                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
            client.requestLocationUpdates(req,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Log.e("location:",locationResult.getLastLocation().toString());
                }
            },null);

        }

        final FusedLocationProviderClient locationClient = LocationServices.
                getFusedLocationProviderClient(this);
                 button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Task<Location> location = locationClient.getLastLocation();
                            location.addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    System.err.println(task.getResult().getLatitude());
                                }
                            });
                        }catch(SecurityException ex){
                            ex.printStackTrace();
                        }
                    }
                });


                 button.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         try {
                             Task<Location> location = locationClient.getLastLocation();
                             location.addOnCompleteListener(new OnCompleteListener<Location>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Location> task) {
                                     textView.setText(Double.toString(task.getResult().getLatitude()));
                                     textView2.setText(Double.toString(task.getResult().getLongitude()));
                                     System.err.println(task.getResult().getLatitude());
                                     System.out.println(task.getResult().getLatitude());
                                 }
                             });
                         }catch(SecurityException ex)
                         {
                             ex.printStackTrace();
                         }
                     }
                 });
    }
}
