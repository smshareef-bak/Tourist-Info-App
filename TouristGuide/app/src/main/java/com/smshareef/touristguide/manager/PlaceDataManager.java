package com.smshareef.touristguide.manager;

import android.os.AsyncTask;

import com.smshareef.touristguide.model.Place;
import com.smshareef.touristguide.utils.PlaceDataListener;
import com.smshareef.touristguide.utils.PlaceUtils;

import java.util.ArrayList;

import static com.smshareef.touristguide.utils.AppConstants.IMAGE_DIR;

/**
 * Created by smsha on 17-05-2017.
 */

public class PlaceDataManager extends AsyncTask<Void, Void, ArrayList<Place>>{

    private PlaceDataListener placeDataListener;

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
        return PlaceUtils.getAllImagesFromDir(IMAGE_DIR);
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

    @Override
    protected void onCancelled(ArrayList<Place> placeArrayList) {
        super.onCancelled(placeArrayList);
        placeDataListener.onPlaceLoadingCancelled();
    }
}
