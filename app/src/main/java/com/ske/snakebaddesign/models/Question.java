package com.ske.snakebaddesign.models;

/**
 * Created by TAWEESOFT on 3/17/16 AD.
 */
public class Question {

    private String question;
    private int answer;

    public Question(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }
}
