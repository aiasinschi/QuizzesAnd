/**
 * Created at Sep 19, 2014, 11:49
 *
 * File Question.java
 */
package org.dasta.quizzes.lawquiz.datautils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 * @since 1.0
 */
public class Question {

    public static final int MAX_NUM_QUESTIONS = 10;

    private String question;

    private List<String> answerTexts;

    private int[] correctAnswers;

    public Question(String question) {
        this.question = question;
        answerTexts = new ArrayList<String>();
        correctAnswers = new int[MAX_NUM_QUESTIONS];
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerTexts() {
        return answerTexts;
    }

    public void addAnswerText(String answer){
        answerTexts.add(answer);
    }

    public int[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void addCorrectAnswer(int pos){
        if ((pos < MAX_NUM_QUESTIONS) && (pos >= 0)) {
            correctAnswers[pos] = 1;
        }
    }

    public double computeScore(int[] answers){
        double s = 0d;
        int ones = 0;
        int ans = 0;
        for(int i = 0; i< answers.length; i++){
            s += answers[i] * correctAnswers[i];
            ones += correctAnswers[i];
            ans += answers[i];
        }
        return (ones != ans) ? 0 : (1.0d * s)/ones;
    }

}
