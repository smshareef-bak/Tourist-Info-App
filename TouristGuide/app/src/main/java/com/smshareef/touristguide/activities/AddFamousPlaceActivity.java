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
import com.smshareef.touristguide.model.FamousPlace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static com.smshareef.touristguide.utils.AppConstants.FAMOUS_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class AddFamousPlaceActivity extends AppCompatActivity {

    //Declaration of variables

    TextView mAddPlaceTv;
    EditText mAddPlaceEt;
    EditText mAddPlaceDescEt;
    ImageView mAddPlaceIv;
    Button mAddPlaceSelectImageBt;
    Button mAddPlaceSaveBt;

    private static final int SELECT_PICTURE = 100;

    String placeName;
    String path;

    //onCreate() is called when the activity first starts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_famous_place);

        mAddPlaceTv = (TextView) findViewById(R.id.addFamousPlaceTv);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NEOTERICc - Regular.ttf");
        mAddPlaceTv.setTypeface(typeface);

        mAddPlaceEt = (EditText) findViewById(R.id.addFamousPlaceEt);
        mAddPlaceDescEt = (EditText) findViewById(R.id.addFamousPlaceDescEt);
        mAddPlaceIv = (ImageView) findViewById(R.id.addFamousPlaceImagePreviewIv);
        mAddPlaceSelectImageBt = (Button) findViewById(R.id.addFamousPlaceSelectImageBt);
        mAddPlaceSaveBt = (Button) findViewById(R.id.addFamousPlaceSaveBt);

        placeName = getIntent().getStringExtra("place_name");

        //Code to select image when a button is pressed

        mAddPlaceSelectImageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        //Save the place when required details are available

        mAddPlaceSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)mAddPlaceIv.getDrawable()).getBitmap();
                String fileName = mAddPlaceEt.getText().toString();
                if(fileName.isEmpty() || fileName.trim().length() <= 0){
                    Toast.makeText(AddFamousPlaceActivity.this, "Place name cannot be empty", Toast.LENGTH_SHORT).show();
                    AddFamousPlaceActivity.super.onBackPressed();
                } else {
                    String fileDesc = mAddPlaceDescEt.getText().toString();
                    Uri filePath = saveImageFile(bitmap, fileName);
                    saveFileDesc(fileDesc, fileName);
                    int mapImageId = android.R.drawable.ic_dialog_map;
                    FamousPlace place = new FamousPlace(fileName, fileDesc, filePath, mapImageId);
                    Intent intent = new Intent();
                    intent.putExtra("place", place);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    AddFamousPlaceActivity.super.onBackPressed();
                }
            }
        });

    }

    //Set the image in the ImageView

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

    //Save the description in .txt file

    private void saveFileDesc(String fileDesc, String fileName) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName +
                                "/" + FAMOUS_PLACE + "/" + fileName + ".txt";

        File file = new File(path);

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(fileDesc);
            writer.flush();
            writer.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Save image file

    private Uri saveImageFile(Bitmap bitmap, String fileName) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName +
                                                        "/" + FAMOUS_PLACE + "/" ;
        fileName = fileName + ".jpg";
        File file = new File(path, fileName);
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

    //Add the menu to activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_button) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
