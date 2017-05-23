package com.smshareef.touristguide.manager;

import android.os.AsyncTask;

import com.smshareef.touristguide.model.Place;
import com.smshareef.touristguide.utils.PlaceDataListener;
import com.smshareef.touristguide.utils.PlaceUtils;

import java.util.ArrayList;

/**
 * Created by smsha on 17-05-2017.
 *
 * DataManager loads all the data available in given directory into ArrayList
 *
 * @author smsha
 */

public class PlaceDataManager extends AsyncTask<Void, Void, ArrayList<Place>>{

    private PlaceDataListener placeDataListener;

    private String dir;

    public PlaceDataManager(PlaceDataListener placeDataListener) {
        this.placeDataListener = placeDataListener;
    }

    public void fetchPlaceData() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Place> doInBackground(Void... params) {
        return PlaceUtils.getAllImagesFromDir(dir);
    }

    @Override
    protected void onPostExecute(ArrayList<Place> placeArrayList) {
        super.onPostExecute(placeArrayList);
        placeDataListener.onPlaceLoaded(placeArrayList);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        placeDataListener.onPlaceLoadingCancelled();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    protected void onCancelled(ArrayList<Place> placeArrayList) {
        super.onCancelled(placeArrayList);
        placeDataListener.onPlaceLoadingCancelled();
    }
}
