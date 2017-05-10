package com.smshareef.touristguide.manager;

/**
 * Created by smsha on 10-05-2017.
 * Acts as a substitute for data query.
 */

public interface OnLoginDataReadyListener {
    void onLoginDataReady(String userID, String password);
}
