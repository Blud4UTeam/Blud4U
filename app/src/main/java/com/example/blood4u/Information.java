package com.example.blood4u;

public class Information {
    public String date;
    public String location;
    public String num;


    public Information(){
    }

    public Information(String date,String location, String num){
        this.date = date;
        this.location = location;
        this.num = num;

    }
    public String getUserDate() {
        return date;
    }
    public String getUserLocation() {
        return location;
    }
    public  String getUserNum() {
        return num;
    }


}
