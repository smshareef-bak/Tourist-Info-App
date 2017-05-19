package com.smshareef.touristguide.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smsha on 12-05-2017.
 */

public class Place implements Parcelable {

    private String placeName;
    private Uri placeImage;

    public Place() {

    }

    public Place(String placeName, Uri placeImage) {
        this.placeName = placeName;
        this.placeImage = placeImage;
    }

    protected Place (Parcel in) {
        placeName = in.readString();
        placeImage = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeParcelable(placeImage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Uri getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(Uri placeImage) {
        this.placeImage = placeImage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
