package com.smshareef.touristguide.manager;

/**
 * Created by smsha on 10-05-2017.
 *
 * DataManager  is generating dummy data. Actual data can be retrieved from
 * Server / Firebase / Database etc.
 *
 * OnDataReadyListener is used to simulate loading of dummy data.
 *
 * @author smshareef
 */

public class LoginDataManager {

    private OnLoginDataReadyListener onLoginDataReadyListener;

    public LoginDataManager(OnLoginDataReadyListener onLoginDataReadyListener) {
        this.onLoginDataReadyListener = onLoginDataReadyListener;
    }

    public void getLoginData() {
        String userID = "user123@example.com";
        String password = "User@1234";

        onLoginDataReadyListener.onLoginDataReady(userID,password);
    }

}
