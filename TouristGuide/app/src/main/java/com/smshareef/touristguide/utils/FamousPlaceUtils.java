package com.smshareef.touristguide.utils;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.smshareef.touristguide.model.FamousPlace;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by smsha on 22-05-2017.
 */

public class FamousPlaceUtils {

    private static final String LOG_TAG = "FamousPlaceUtils";

    /**
     * @param dir dir or dir path inside externalStorage
     * @return list of all images inside directory
     */

    public static ArrayList<FamousPlace> getAllImagesFromDir(String dir) {

        ArrayList<FamousPlace> placeArrayList = new ArrayList<>();
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
            FamousPlace placeDetails = new FamousPlace();
            placeDetails.setFamousPlaceName(file.getName());
            placeDetails.setFamousPlaceImage(Uri.fromFile(file));
            placeDetails.setMapImageId(android.R.drawable.ic_dialog_map);
            placeArrayList.add(placeDetails);
        }

        final File[] textFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".txt") ) {
                    return true;
                }
                return false;
            }
        });

        for(int i=0;i<placeArrayList.size();i++) {
            for (File file : textFiles) {
                if (FilenameUtils.removeExtension(file.getName()).equals(FilenameUtils.removeExtension((placeArrayList.get(i).getFamousPlaceName())))){
                    StringBuilder text = new StringBuilder();
                    String line;

                    try
                    {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        while ((line = bufferedReader.readLine()) != null){
                            text.append(line);
                            text.append('\n');
                        }
                        bufferedReader.close();
                    }
                    catch (Exception e)
                    {
                        Log.e("LOG_TAG","File read exception");
                    }
                    FamousPlace placeDetails = new FamousPlace();
                    placeDetails.setFamousPlaceName(placeArrayList.get(i).getFamousPlaceName());
                    placeDetails.setFamousPlaceImage(placeArrayList.get(i).getFamousPlaceImage());
                    placeDetails.setMapImageId(placeArrayList.get(i).getMapImageId());
                    placeDetails.setFamousPlaceDescription(text.toString());
                    placeArrayList.set(i,placeDetails);
                }
            }
        }

        return placeArrayList;

    }

}
