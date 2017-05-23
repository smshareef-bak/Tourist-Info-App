package com.smshareef.touristguide.utils;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.smshareef.touristguide.model.Place;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by smsha on 17-05-2017.
 */

public class PlaceUtils {

    private static final String LOG_TAG = "PlaceUtils";

    /**
     * @param dir directory path inside externalStorage
     * @return list of all images inside directory
     */

    public static ArrayList<Place> getAllImagesFromDir(String dir) {

        ArrayList<Place> placeArrayList = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dir + "/";

        File directory = new File(path);
        if (!directory.exists()) {
            Log.d(LOG_TAG, "Directory doesn't exist! Now creating...");
            File f = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/",File.separator + dir);
            f.mkdirs();
            return placeArrayList;
        }
        final File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jpg") || name.endsWith(".png") ) {
                    return true;
                }
                return false;
            }
        });
        for (File file : files) {
            Place placeDetails = new Place();
            placeDetails.setPlaceName(file.getName());
            placeDetails.setPlaceImage(Uri.fromFile(file));
            placeArrayList.add(placeDetails);
        }

        return placeArrayList;

    }



}
