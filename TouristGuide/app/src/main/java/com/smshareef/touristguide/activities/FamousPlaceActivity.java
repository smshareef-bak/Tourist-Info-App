package com.smshareef.touristguide.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.adapters.FamousPlaceRecyclerAdapter;
import com.smshareef.touristguide.manager.FamousPlaceDataManager;
import com.smshareef.touristguide.model.FamousPlace;
import com.smshareef.touristguide.utils.FamousPlaceDataListener;
import com.smshareef.touristguide.utils.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

import static com.smshareef.touristguide.utils.AppConstants.FAMOUS_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class FamousPlaceActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener, FamousPlaceDataListener {

    //----------------------------------------------------------------------------------------------
    //Views
    //----------------------------------------------------------------------------------------------
    private FloatingActionButton fab;

    private RecyclerView recyclerView;

    //----------------------------------------------------------------------------------------------
    //Other fields
    //----------------------------------------------------------------------------------------------

    private FamousPlaceRecyclerAdapter famousPlaceRecyclerAdapter;
    private static final int REQUEST_CODE = 102;
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

    private void loadData() {
        FamousPlaceDataManager placeDataManager = new FamousPlaceDataManager(this);
        placeDataManager.setDir(RESOURCES_DIR + "/" + placeName + "/" + FAMOUS_PLACE + "/");
        placeDataManager.fetchFamousPlaceData();
    }

    @Override
    public void onItemClicked(Bundle bundle) {
        Toast.makeText(this, "Click on MAP icon to view location on Map", Toast.LENGTH_LONG).show();
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
}
