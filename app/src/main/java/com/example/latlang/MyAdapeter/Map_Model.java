package com.example.latlang.MyAdapeter;

import androidx.room.PrimaryKey;

public class Map_Model {
    @PrimaryKey(autoGenerate = true)
    int id;
    double GOOGLE_MAP;
    double GOOGLE_MAP_DIFFRENCE;
    double OSM_MAP;
    double OSM_MAP_DIFFRENCE;

    //Constructor

    public Map_Model(double GOOGLE_MAP, double GOOGLE_MAP_DIFFRENCE, double OSM_MAP, double OSM_MAP_DIFFRENCE) {
        this.GOOGLE_MAP = GOOGLE_MAP;
        this.GOOGLE_MAP_DIFFRENCE = GOOGLE_MAP_DIFFRENCE;
        this.OSM_MAP = OSM_MAP;
        this.OSM_MAP_DIFFRENCE = OSM_MAP_DIFFRENCE;
    }

    //getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGOOGLE_MAP() {
        return GOOGLE_MAP;
    }

    public void setGOOGLE_MAP(double GOOGLE_MAP) {
        this.GOOGLE_MAP = GOOGLE_MAP;
    }

    public double getGOOGLE_MAP_DIFFRENCE() {
        return GOOGLE_MAP_DIFFRENCE;
    }

    public void setGOOGLE_MAP_DIFFRENCE(double GOOGLE_MAP_DIFFRENCE) {
        this.GOOGLE_MAP_DIFFRENCE = GOOGLE_MAP_DIFFRENCE;
    }

    public double getOSM_MAP() {
        return OSM_MAP;
    }

    public void setOSM_MAP(double OSM_MAP) {
        this.OSM_MAP = OSM_MAP;
    }

    public double getOSM_MAP_DIFFRENCE() {
        return OSM_MAP_DIFFRENCE;
    }

    public void setOSM_MAP_DIFFRENCE(double OSM_MAP_DIFFRENCE) {
        this.OSM_MAP_DIFFRENCE = OSM_MAP_DIFFRENCE;
    }
}
