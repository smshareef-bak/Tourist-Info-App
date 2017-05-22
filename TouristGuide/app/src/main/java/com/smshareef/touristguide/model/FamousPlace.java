package com.smshareef.touristguide.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smsha on 22-05-2017.
 */

public class FamousPlace implements Parcelable {
    private String famousPlaceName;
    private String famousPlaceDescription;
    private Uri famousPlaceImage;

    public FamousPlace() {

    }

    public FamousPlace(String famousPlaceName, String famousPlaceDescription, Uri famousPlaceImage) {
        this.famousPlaceName = famousPlaceName;
        this.famousPlaceDescription = famousPlaceDescription;
        this.famousPlaceImage = famousPlaceImage;
    }

    protected FamousPlace (Parcel in) {
        famousPlaceName = in.readString();
        famousPlaceDescription = in.readString();
        famousPlaceImage = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(famousPlaceName);
        dest.writeString(famousPlaceDescription);
        dest.writeParcelable(famousPlaceImage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FamousPlace> CREATOR = new Creator<FamousPlace>() {
        @Override
        public FamousPlace createFromParcel(Parcel in) {
            return new FamousPlace(in);
        }

        @Override
        public FamousPlace[] newArray(int size) {
            return new FamousPlace[size];
        }
    };

    public String getFamousPlaceName() {
        return famousPlaceName;
    }

    public void setFamousPlaceName(String famousPlaceName) {
        this.famousPlaceName = famousPlaceName;
    }

    public Uri getFamousPlaceImage() {
        return famousPlaceImage;
    }

    public void setFamousPlaceImage(Uri famousPlaceImage) {
        this.famousPlaceImage = famousPlaceImage;
    }

    public String getFamousPlaceDescription() {
        return famousPlaceDescription;
    }

    public void setFamousPlaceDescription(String famousPlaceDescription) {
        this.famousPlaceDescription = famousPlaceDescription;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
