package com.smshareef.touristguide.activities;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.adapters.PlaceRecyclerAdapter;
import com.smshareef.touristguide.manager.PlaceDataManager;
import com.smshareef.touristguide.model.Place;
import com.smshareef.touristguide.utils.OnRecyclerViewItemClickListener;
import com.smshareef.touristguide.utils.PlaceDataListener;

import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.smshareef.touristguide.utils.PermissionUtils.hasPermission;


public class MainActivity extends AppCompatActivity implements PlaceDataListener, View.OnClickListener, OnRecyclerViewItemClickListener {

    private static final int GRID_COLUMN_COUNT = 2;
    boolean doubleBackToExitPressedOnce = false; //Boolean variable to to check if back button is pressed

    //----------------------------------------------------------------------------------------------
    //Constants
    //----------------------------------------------------------------------------------------------
    private static final int REQUEST_READ_STORAGE = 1;
    private static final int REQUEST_WRITE_STORAGE = 2;
    private final static int ALL_PERMISSION_RESULT = 107;

    private static final int SPAN_COUNT = 3;

    //----------------------------------------------------------------------------------------------
    //Views
    //----------------------------------------------------------------------------------------------
    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    //----------------------------------------------------------------------------------------------
    //Other fields
    //----------------------------------------------------------------------------------------------

    private PlaceRecyclerAdapter placeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        placeRecyclerAdapter = new PlaceRecyclerAdapter(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(placeRecyclerAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_COLUMN_COUNT);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


        if (mayRequestPermission()) {
            loadData();
        }

    }

    private void loadData() {
        PlaceDataManager placeDataManager = new PlaceDataManager(this);
        placeDataManager.fetchPlaceData();
    }

    //----------------------------------------------------------------------------------------------
    //Storage Permissions handling
    //----------------------------------------------------------------------------------------------

    /**
     * Runtime  Permissions are only applicable for Android M and above
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean mayRequestPermission() {

        //if permission is already granted then skip
        if (hasPermission(this, WRITE_EXTERNAL_STORAGE) && hasPermission(this, READ_EXTERNAL_STORAGE)) {
            return true;
        }
        /**
         * Gets whether you should show UI with rationale for requesting a permission.
         * You should do this only if you do not have the permission and the context in
         * which the permission is requested does not clearly communicate to the user
         * what would be the benefit from granting this permission.
         * <p>
         * For example, if you write a camera app, requesting the camera permission
         * would be expected by the user and no rationale for why it is requested is
         * needed. If however, the app needs location for tagging photos then a non-tech
         * savvy user may wonder how location is related to taking photos. In this case
         * you may choose to show UI with rationale of requesting this permission.
         * </p>
         * */
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(fab, R.string.permission_storage_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                        }
                    });
        } else if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
            Snackbar.make(fab, R.string.permission_storage_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                        }
                    });
        } else {
            requestPermissions(new String[]{
                    WRITE_EXTERNAL_STORAGE,
                    READ_EXTERNAL_STORAGE}, ALL_PERMISSION_RESULT);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, R.string.write_permission_granted, Toast.LENGTH_SHORT).show();
            }
            //permission denied : Try asking again with appropriate message
            else if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(fab, R.string.permission_storage_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                            }
                        });

            }
        } else if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, R.string.read_permission_granted, Toast.LENGTH_SHORT).show();

            }
            //permission denied : Try asking again with appropriate message
            else if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {

                Snackbar.make(fab, R.string.permission_storage_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                            }
                        });
            }
        } else if (requestCode == ALL_PERMISSION_RESULT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, R.string.both_permission_granted, Toast.LENGTH_SHORT).show();

                loadData();
            } else {
                Snackbar.make(fab, R.string.permission_storage_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{
                                        WRITE_EXTERNAL_STORAGE,
                                        READ_EXTERNAL_STORAGE}, ALL_PERMISSION_RESULT);
                            }
                        });
            }
        }
    }




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {  //If back button is already pressed before 2 seconds, back button functionality is implemented.
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true; //back button is pressed once
        Toast.makeText(this, "Press back button again to exit...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() { //2 sec time out for press of back button
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onPlaceLoaded(ArrayList<Place> placeArrayList) {
        placeRecyclerAdapter.setData(placeArrayList);
    }

    @Override
    public void onPlaceLoadingFailed() {

    }

    @Override
    public void onPlaceLoadingCancelled() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
        }
    }



    @Override
    public void onItemClicked(Bundle bundle) {

    }
}
