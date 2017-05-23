package com.smshareef.touristguide.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.smshareef.touristguide.utils.AppConstants.PLACES_IMAGE_DIR;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;


public class AddPlaceActivity extends AppCompatActivity {

    //Declaration of variables

    TextView mAddPlaceTv;
    EditText mAddPlaceEt;
    ImageView mAddPlaceIv;
    Button mAddPlaceSelectImageBt;
    Button mAddPlaceSaveBt;

    private static final int SELECT_PICTURE = 100;

    //onCreate() is called when activity starts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        mAddPlaceTv = (TextView) findViewById(R.id.addPlaceTv);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NEOTERICc - Regular.ttf");
        mAddPlaceTv.setTypeface(typeface);

        mAddPlaceEt = (EditText) findViewById(R.id.addPlaceEt);
        mAddPlaceIv = (ImageView) findViewById(R.id.addPlaceImagePreviewIv);
        mAddPlaceSelectImageBt = (Button) findViewById(R.id.addPlaceSelectImageBt);
        mAddPlaceSaveBt = (Button) findViewById(R.id.addPlaceSaveBt);

        //Open image chooser when a button is clicked

        mAddPlaceSelectImageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        //Save place when respective button is pressed

        mAddPlaceSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)mAddPlaceIv.getDrawable()).getBitmap();
                String fileName= mAddPlaceEt.getText().toString();
                if(fileName.isEmpty()|| fileName.trim().length() <= 0){
                    Toast.makeText(AddPlaceActivity.this, "Place name cannot be empty", Toast.LENGTH_SHORT).show();
                    AddPlaceActivity.super.onBackPressed();
                } else {
                    Uri filePath = saveImageFile(bitmap, fileName);
                    Place place = new Place(fileName, filePath);
                    Intent intent = new Intent();
                    intent.putExtra("place", place);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    AddPlaceActivity.super.onBackPressed();
                }
            }
        });

    }

    //Show the selected image in a ImageView

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    mAddPlaceIv.setImageURI(selectedImageUri);
                }
            }
        }
    }

    //save image into a file

    public Uri saveImageFile(Bitmap bitmap, String fileName) {
        String filePath = getPathname();
        fileName = fileName + ".jpg";
        File file = new File(filePath, fileName);
        if (file.exists ()) {
            Toast.makeText(this, "Place already present", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
            return Uri.fromFile(file);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    private String getPathname() {

        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR +
                                        "/" +PLACES_IMAGE_DIR;
    }

    //Add menu to activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_button) {
            Intent intent = new Intent(AddPlaceActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
