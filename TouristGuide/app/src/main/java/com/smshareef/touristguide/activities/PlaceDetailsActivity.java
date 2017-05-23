package com.smshareef.touristguide.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smshareef.touristguide.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static com.smshareef.touristguide.utils.AppConstants.IMAGE_DESC;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class PlaceDetailsActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private static final String LOG_TAG = "PlaceDetailsActivity";
    public static final int EDIT_TEXT = 102;
    private String line;
    private StringBuilder text;
    private FloatingActionButton fabAddDesc;
    private String edit = "To add the description please click on EDIT Floating Action Button";

    private String placeName;
    private Uri placeImage;

    private Button gallery;
    private Button famousPlaces;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabAddDesc = (FloatingActionButton) findViewById(R.id.fabAddDesc);

        //To show the details of a place

        placeName = getIntent().getStringExtra("Place_Name");
        placeName = FilenameUtils.removeExtension(placeName);
        setTitle(placeName);
        placeImage = getIntent().getParcelableExtra("Place_Image");
        mImageView = (ImageView) findViewById(R.id.imageViewPlaceDetails);
        mTextView = (TextView) findViewById(R.id.textViewPlaceDetails);
        Picasso.with(this)
                .load(placeImage)
                .fit()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(mImageView);

        mTextView.setMovementMethod(new ScrollingMovementMethod());

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName + "/" + IMAGE_DESC;

        //To read description file and display in a TextView

        //If Folder/File is absent, create it

        File file = new File(path);
        if (!file.exists()) {
            Log.d(LOG_TAG, "File doesn't exist! Now creating...");
            File f = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+RESOURCES_DIR,File.separator + placeName);
            f.mkdirs();
            try {
                File createFile = new File(f, IMAGE_DESC);
                FileWriter writer = new FileWriter(createFile);
                writer.append(edit);
                writer.flush();
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        text = new StringBuilder();

        //Read file and show in TextView

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null){
                text.append(line);
                text.append('\n');
            }
            bufferedReader.close();
            mTextView.setText(text.toString());

        }
        catch (Exception e)
        {
            Log.e("LOG_TAG","File read exception");
        }

        //Show activity to edit description when a fab is clicked

        fabAddDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailsActivity.this,EditTextActivity.class);
                intent.putExtra("Place_Name", placeName);
                PlaceDetailsActivity.this.startActivity(intent);
                finish();
            }
        });

        gallery = (Button) findViewById(R.id.buttonGallery);

        //Show the images in gallery

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailsActivity.this,GalleryActivity.class);
                intent.putExtra("Place_Name", placeName);
                PlaceDetailsActivity.this.startActivity(intent);
            }
        });

        //Show the famous places in selected place

        famousPlaces = (Button) findViewById(R.id.buttonFamousPlaces);

        famousPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailsActivity.this, FamousPlaceActivity.class);
                intent.putExtra("Place_Name", placeName);
                startActivity(intent);
            }
        });

    }

    //Add menu to activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    //To close all activities and goto Login activity when a button is pressed

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_button) {
            Intent intent = new Intent(PlaceDetailsActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
