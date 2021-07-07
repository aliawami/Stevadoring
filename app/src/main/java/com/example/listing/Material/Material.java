package com.example.listing.Material;

import android.os.Parcel;
import android.os.Parcelable;

public class Material implements Parcelable {
//public class Material {
    private String name;
    private String quan;
    private String driver;
    private String vehicle;
    private Boolean loaded = false, found = false;
    private int pic;



    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    protected Material(Parcel in) {
        name = in.readString();
        quan = in.readString();
//        pic = in.readInt();
        driver = in.readString();
        vehicle  = in.readString();
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

//    public Material(String name, String quan, Boolean loaded, int pic, String driver, String vehicle, boolean found) {
//        this.name = name;
//        this.quan = quan;
//        this.loaded = loaded;
//        this.pic = pic;
//        this.driver = driver;
//        this.vehicle = vehicle;
//        this.found = found;
//    }

    public Material(String name, String quan, Boolean loaded, String driver, String vehicle, boolean found) {
        this.name = name;
        this.quan = quan;
        this.loaded = loaded;

        this.driver = driver;
        this.vehicle = vehicle;
        this.found = found;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(quan);
        dest.writeInt(pic);
        dest.writeString(driver);
        dest.writeString(vehicle);
    }
}
