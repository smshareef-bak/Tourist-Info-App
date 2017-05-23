package com.smshareef.touristguide.utils;

import com.smshareef.touristguide.model.FamousPlace;

import java.util.ArrayList;

/**
 * Created by smsha on 22-05-2017.
 *
 * @author smshass
 */

public interface FamousPlaceDataListener {

    void onFamousPlaceLoaded(ArrayList<FamousPlace> placeArrayList);
    void onFamousPlaceLoadingFailed();
    void onFamousPlaceLoadingCancelled();

}
