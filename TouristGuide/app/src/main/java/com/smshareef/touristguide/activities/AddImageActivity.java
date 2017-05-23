package com.smshareef.touristguide.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class AddImageActivity extends AppCompatActivity {

    //Declaration of variables

    TextView mAddGalleryTv;
    ImageView mAddGalleryIv;
    Button mAddGallerySelectImageBt;
    Button mAddGallerySaveBt;

    static int num = 0;

    private static final int SELECT_PICTURE = 100;

    String placeName;

    //onCreate() is called when an Activity starts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        mAddGalleryTv = (TextView) findViewById(R.id.addGalleryTv);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NEOTERICc - Regular.ttf");
        mAddGalleryTv.setTypeface(typeface);

        mAddGalleryIv = (ImageView) findViewById(R.id.addGalleryImagePreviewIv);
        mAddGallerySelectImageBt = (Button) findViewById(R.id.addGallerySelectImageBt);
        mAddGallerySaveBt = (Button) findViewById(R.id.addGallerySaveBt);

        //Select the image
        mAddGallerySelectImageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        placeName = getIntent().getStringExtra("place_name");
        num = getIntent().getIntExtra("image_count", 0);

        //Save the image when save button is clicked

        mAddGallerySaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)mAddGalleryIv.getDrawable()).getBitmap();
                num += 1 ;
                String fileName= "Image" + num;
                Uri filePath = saveImageFile(bitmap, fileName);
                Place gallery = new Place(fileName, filePath);
                Intent intent = new Intent();
                intent.putExtra("gallery", gallery);
                setResult(Activity.RESULT_OK, intent);
                finish();
                AddImageActivity.super.onBackPressed();
            }
        });

    }

    //Display the selected image in an ImageView

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    mAddGalleryIv.setImageURI(selectedImageUri);
                }
            }
        }
    }

    //Save image to a file

    public Uri saveImageFile(Bitmap bitmap, String fileName) {
        String filePath = getPathname();
        fileName = fileName + ".jpg";
        File file = new File(filePath, fileName);
        if (file.exists ()) {
            Toast.makeText(this, "Image already present", Toast.LENGTH_SHORT).show();
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
                "/" + placeName;
    }

}
