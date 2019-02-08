package com.les_indecis.ing3.esipe.les_indecis_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class ParkingSpot implements Parcelable{

    static ArrayList<ParkingSpot> listSpots = new ArrayList<ParkingSpot>();

    public static final Parcelable.Creator<ParkingSpot> CREATOR =
            new Parcelable.Creator<ParkingSpot>(){
                @Override
                public ParkingSpot createFromParcel(Parcel source){
                    return new ParkingSpot(source);
                }
                @Override
                public ParkingSpot[] newArray(int size){
                    return new ParkingSpot[size];
                }
            };

    private Long id;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private Double longitude;
    private Double latitude;
    private int capacity;
    private int occupancy;
    private String designation;
    private String city;

    public ParkingSpot(){};

    public ParkingSpot(Long id, Double longitude, Double latitude, int capacity, int occupancy, String designation, String city) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.designation = designation;
        this.city = city;
    }

    public ParkingSpot(Parcel in){
        //this.id = in.readLong();
        this.id = in.readLong();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.capacity = in.readInt();
        this.occupancy = in.readInt();
        this.designation = in.readString();
        this.city = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        //dest.writeLong(this.id);
        dest.writeLong(this.id);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeInt(this.capacity);
        dest.writeInt(this.occupancy);
        dest.writeString(this.designation);
        dest.writeString(this.city);
    }
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

}
