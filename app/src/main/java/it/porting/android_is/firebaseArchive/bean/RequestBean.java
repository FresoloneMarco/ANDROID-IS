package it.porting.android_is.firebaseArchive.bean;

import com.google.firebase.Timestamp;

/**
 * Bean per requests
 */


public class RequestBean {
    private int id;
    private String level;
    private int serial;
    private int validated_cfu;
    private String year;
    private Timestamp release_date;
    private Timestamp expiry_date;


    public RequestBean(int id, String level, int serial, int validated_cfu, String year, Timestamp release_date, Timestamp expiry_date) {
        this.id = id;
        this.level = level;
        this.serial = serial;
        this.validated_cfu = validated_cfu;
        this.year = year;
        this.release_date = release_date;
        this.expiry_date = expiry_date;
    }

    public RequestBean(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getValidated_cfu() {
        return validated_cfu;
    }

    public void setValidated_cfu(int validated_cfu) {
        this.validated_cfu = validated_cfu;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Timestamp getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Timestamp release_date) {
        this.release_date = release_date;
    }

    public Timestamp getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Timestamp expiry_date) {
        this.expiry_date = expiry_date;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", serial=" + serial +
                ", validated_cfu=" + validated_cfu +
                ", year='" + year + '\'' +
                ", release_date=" + release_date +
                ", expiry_date=" + expiry_date +
                '}';
    }
}
