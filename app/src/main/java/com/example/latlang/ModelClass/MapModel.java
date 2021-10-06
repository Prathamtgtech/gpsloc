package com.example.latlang.ModelClass;

import androidx.room.PrimaryKey;

public class MapModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    double G_lat;
    double G_lang;
    double M_lat;
    double M_lang;
    double Diff;

    public MapModel() {
        G_lat =G_lat;
        G_lang = G_lang;
        M_lat = M_lat;
        M_lang = M_lang;
        Diff = Diff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getG_lat() {
        return G_lat;
    }

    public void setG_lat(double g_lat) {
        G_lat = g_lat;
    }

    public double getG_lang() {
        return G_lang;
    }

    public void setG_lang(double g_lang) {
        G_lang = g_lang;
    }

    public double getM_lat() {
        return M_lat;
    }

    public void setM_lat(double m_lat) {
        M_lat = m_lat;
    }

    public double getM_lang() {
        return M_lang;
    }

    public void setM_lang(double m_lang) {
        M_lang = m_lang;
    }

    public double getDiff() {
        return Diff;
    }

    public void setDiff(double diff) {
        Diff = diff;
    }
}
