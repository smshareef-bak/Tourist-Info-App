package com.smshareef.touristguide.manager;

import android.os.AsyncTask;

import com.smshareef.touristguide.model.FamousPlace;
import com.smshareef.touristguide.utils.FamousPlaceDataListener;
import com.smshareef.touristguide.utils.FamousPlaceUtils;

import java.util.ArrayList;

/**
 * Created by smsha on 22-05-2017.
 */

public class FamousPlaceDataManager extends AsyncTask<Void, Void, ArrayList<FamousPlace>> {

    private FamousPlaceDataListener famousPlaceDataListener;

    private String dir;

    public FamousPlaceDataManager(FamousPlaceDataListener famousPlaceDataListener) {
        this.famousPlaceDataListener = famousPlaceDataListener;
    }

    public void fetchFamousPlaceData() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<FamousPlace> doInBackground(Void... params) {
        return FamousPlaceUtils.getAllImagesFromDir(dir);
    }

    @Override
    protected void onPostExecute(ArrayList<FamousPlace> famousPlaceArrayList) {
        super.onPostExecute(famousPlaceArrayList);
        famousPlaceDataListener.onFamousPlaceLoaded(famousPlaceArrayList);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        famousPlaceDataListener.onFamousPlaceLoadingCancelled();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    protected void onCancelled(ArrayList<FamousPlace> famousPlaceArrayList) {
        super.onCancelled(famousPlaceArrayList);
        famousPlaceDataListener.onFamousPlaceLoadingCancelled();
    }
}
