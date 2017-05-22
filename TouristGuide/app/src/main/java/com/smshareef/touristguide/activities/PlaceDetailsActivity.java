package com.smshareef.touristguide.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName + "/" + IMAGE_DESC;

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

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailsActivity.this,GalleryActivity.class);
                intent.putExtra("Place_Name", placeName);
                PlaceDetailsActivity.this.startActivity(intent);
            }
        });

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


}
