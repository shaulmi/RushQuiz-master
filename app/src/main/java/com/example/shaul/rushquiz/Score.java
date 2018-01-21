package com.example.shaul.rushquiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shaul on 09/10/2017.
 */

public class Score {

    private String User;

    private int Result;

    private String Date;


    public Score(String user, int result,String date) {
        this.User = user;
        this.Result = result;
        this.Date = date;
    }

    public String getUser() {

        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getResult() {

        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
