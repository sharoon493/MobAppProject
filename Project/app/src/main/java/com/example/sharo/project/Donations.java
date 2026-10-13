package com.example.sharo.project;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Donations {

    public long Donate_id;
    public String UserName;
    public String Donate_Descr;
    public String Condition;
    public String MobileNumber;
    public String Email;
    public double Longitude;
    public double Latitude;
    public String date;
    public String Category;
    public String Image;

    public Donations(){}
    public Donations(long Donate_id,String UserName,String Email,String Donate_Descr,String Condition,
                     String MobileNumber,double Longitude,double Latitude,String Category, String Image){//,){

        this.Donate_id = Donate_id;
        this.UserName = UserName;
        this.Email = Email;
        this.Donate_Descr =Donate_Descr;
        this.Condition =Condition;
        this.MobileNumber =MobileNumber;
        this.Latitude =Longitude;
        this.Longitude =Latitude;
        this.date =java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).toString();
        this.Category =Category;
        this.Image = Image;

    }
}
