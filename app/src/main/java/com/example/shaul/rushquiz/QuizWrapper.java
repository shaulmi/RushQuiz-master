package com.example.shaul.rushquiz;

import android.app.Activity;

/**
 * Created by shaul on 04/07/2017.
 */

public class QuizWrapper {

    private int id;

    private String question;

    private String answers;

    private String OPTA;

    private String OPTB;

    private String OPTC;

    private String OPTD;

    private String category;


    public QuizWrapper(){

        id = 0;
        question="";
        OPTA = "";
        OPTB = "";
        OPTC = "";
        OPTD = "";
        answers = "";
        category = "";

    }


    public QuizWrapper(String question,String OPTA,String OPTB,String OPTC,String OPTD,String answers ){

        this.question = question;

        this.OPTA = OPTA;

        this.OPTB = OPTB;

        this.OPTC = OPTC;

        this.OPTD = OPTD;

        this.answers = answers;

    }


    public int getId()
    {
        return id;
    }

    public String getCategory() {return category;}

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getOPTA()
    {
        return OPTA;
    }

    public String getOPTB()
    {
        return OPTB;
    }

    public String getOPTC()
    {
        return OPTC;
    }
    public String getOPTD()
    {
        return OPTD;
    }

    public String getAnswers() {
        return answers;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setAnswers(String answers) {

        this.answers = answers;
    }


    public void setId(int id) {

        this.id = id;
    }

    public void setQuestion(String question) {

        this.question = question;
    }

    public void setOPTA (String OPTA) {

        this.OPTA =OPTA;
    }

    public void setOPTB (String OPTB) {

        this.OPTB = OPTB;
    }

    public void setOPTC (String OPTC) {

        this.OPTC = OPTC;
    }

    public void setOPTD (String OPTD) {

        this.OPTD = OPTD;
    }

}

