package com.example.sharo.project;

import android.service.notification.StatusBarNotification;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Requests {
    public long Request_id;
    public String Username;
    public String Email;
    public String Request_Desc;
    public String Reason;
    public String MobileNumber;
    public String City;
    public String Country;
    public String Image;
    public String date;

    public Requests(){

    }

    public Requests(long request_id, String username,
                    String email, String request_Desc, String reason,
                    String mobileNumber, String city, String country,
                    String image) {
        Request_id = request_id;
        Username = username;
        Email = email;
        Request_Desc = request_Desc;
        Reason = reason;
        MobileNumber = mobileNumber;
        City = city;
        Country = country;
        Image = image;
        this.date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).toString();
    }
}
