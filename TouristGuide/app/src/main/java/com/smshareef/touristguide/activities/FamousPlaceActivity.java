package com.smshareef.touristguide.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.adapters.FamousPlaceRecyclerAdapter;
import com.smshareef.touristguide.manager.FamousPlaceDataManager;
import com.smshareef.touristguide.model.FamousPlace;
import com.smshareef.touristguide.utils.FamousPlaceDataListener;
import com.smshareef.touristguide.utils.OnRecyclerViewFamousPlaceClickListener;

import java.util.ArrayList;

import static com.smshareef.touristguide.utils.AppConstants.FAMOUS_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.KEY_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class FamousPlaceActivity extends AppCompatActivity implements OnRecyclerViewFamousPlaceClickListener, FamousPlaceDataListener {

    //----------------------------------------------------------------------------------------------
    //Views
    //----------------------------------------------------------------------------------------------

    private RecyclerView recyclerView;

    private FamousPlaceRecyclerAdapter famousPlaceRecyclerAdapter;
    private static final int REQUEST_CODE = 102;
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //reuse of main activity layout for recycler view

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //open activity to add a new famous place

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FamousPlaceActivity.this, AddFamousPlaceActivity.class);
                intent.putExtra("place_name", placeName);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //Set adapter to RecyclerView

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        famousPlaceRecyclerAdapter = new FamousPlaceRecyclerAdapter(this);

        recyclerView.setHasFixedSize(true);

        famousPlaceRecyclerAdapter.setOnRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(famousPlaceRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        placeName = getIntent().getStringExtra("Place_Name");

        loadData();
    }

    //To instantiate the ArrayList

    private void loadData() {
        FamousPlaceDataManager placeDataManager = new FamousPlaceDataManager(this);
        placeDataManager.setDir(RESOURCES_DIR + "/" + placeName + "/" + FAMOUS_PLACE + "/");
        placeDataManager.fetchFamousPlaceData();
    }

    // To show the place on map when item is clicked

    @Override
    public void onItemClicked(Bundle bundle, View view) {

        if(view.getId() == R.id.imageButton){
            Intent intent = new Intent(FamousPlaceActivity.this, FamousPlaceMapsActivity.class);
            FamousPlace famousPlace = bundle.getParcelable(KEY_PLACE);
            String famousPlaceName;
            famousPlaceName = famousPlace.getFamousPlaceName();
            intent.putExtra("famous_place_name",famousPlaceName);
            FamousPlaceActivity.this.startActivity(intent);

        } else {
                Toast.makeText(this, "Please click on MAP icon to show place on map", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFamousPlaceLoaded(ArrayList<FamousPlace> placeArrayList) {
        famousPlaceRecyclerAdapter.setData(placeArrayList);
    }

    @Override
    public void onFamousPlaceLoadingFailed() {

    }

    @Override
    public void onFamousPlaceLoadingCancelled() {

    }

    //adds new item to ArrayList once its available from the called activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if(b != null) {
                    FamousPlace place = b.getParcelable("place");
                    famousPlaceRecyclerAdapter.addData(place);
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Add menu to activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    //Goto login screen when a button is pressed

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_button) {
            Intent intent = new Intent(FamousPlaceActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
