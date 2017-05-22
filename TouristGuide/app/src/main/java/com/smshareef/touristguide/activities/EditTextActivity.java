package com.smshareef.touristguide.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.smshareef.touristguide.R;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static com.smshareef.touristguide.utils.AppConstants.IMAGE_DESC;
import static com.smshareef.touristguide.utils.AppConstants.RESOURCES_DIR;

public class EditTextActivity extends AppCompatActivity {

    private TextView mEditDescEt;
    private String placeName;

    private StringBuilder text;
    private String line;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        placeName = getIntent().getStringExtra("Place_Name");
        placeName = FilenameUtils.removeExtension(placeName);

        mEditDescEt = (TextView) findViewById(R.id.editDescEt);

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName + "/" + IMAGE_DESC;

        File file = new File(path);

        text = new StringBuilder();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null){
                text.append(line);
                text.append('\n');
            }
            bufferedReader.close();
            mEditDescEt.setText(text.toString());
        }
        catch (Exception e)
        {
            Log.e("LOG_TAG","File read exception");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_desc_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_desc_save) {

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RESOURCES_DIR + "/" + placeName + "/" + IMAGE_DESC;

            File file = new File(path);

            try {
                String text = mEditDescEt.getText().toString();
                FileWriter writer = new FileWriter(file);
                writer.append(text);
                writer.flush();
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
        Toast.makeText(this, "Description changed successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
