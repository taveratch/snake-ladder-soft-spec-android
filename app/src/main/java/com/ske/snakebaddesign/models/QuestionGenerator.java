package com.ske.snakebaddesign.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TAWEESOFT on 3/17/16 AD.
 */
public class QuestionGenerator {
    private List<Question> questions;
    private int numberOfQuestion;
    public QuestionGenerator(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
        questions = new ArrayList<>();
    }

    public void generateQuestion(){
        for(int i = 0;i<numberOfQuestion;i++){
            int operation = 1 + new Random().nextInt(3);
            Question question=null;
            int a = 20 + new Random().nextInt(40);
            int b = 1 + new Random().nextInt(20);
            switch(operation){
                case 1 : // +
                    question = new Question(String.format("%d + %d",a,b) ,a+b);
                    break;
                case 2 :
                    question = new Question(String.format("%d - %d",a,b), a-b);
                    break;
                case 3 :
                    question = new Question(String.format("%d * %d", a,b), a*b);
                    break;
            }
            questions.add(question);
        }
    }

    public List<Question> getQuestions(){
        return questions;
    }
}
