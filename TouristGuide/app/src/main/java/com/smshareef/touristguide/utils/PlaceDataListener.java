package com.smshareef.touristguide.utils;

import com.smshareef.touristguide.model.Place;

import java.util.ArrayList;

/**
 * Created by smsha on 17-05-2017.
 */

public interface PlaceDataListener {

    void onPlaceLoaded(ArrayList<Place> placeArrayList);
    void onPlaceLoadingFailed();
    void onPlaceLoadingCancelled();
}
