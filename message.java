package com.example.prabhat.q;

import java.util.ArrayList;

public class message {
    public ArrayList<String> al;
    message(String q,ArrayList<String> l)
    {
        this.al=l;
        question=q;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String question;

    public ArrayList<String> getAl() {
        return al;
    }

    public void setAl(ArrayList<String> al) {
        this.al = al;
    }


}
