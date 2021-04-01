package com.example.blood4u;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("location", location);

        result.put("num", num);
        return  result;

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
