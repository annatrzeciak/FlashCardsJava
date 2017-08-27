package pl.app.flashcards.modelFx;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LearningFx {
    private StringProperty word= new SimpleStringProperty();
    private StringProperty answerA= new SimpleStringProperty();
    private StringProperty answerB= new SimpleStringProperty();
    private StringProperty answerC= new SimpleStringProperty();
    private StringProperty goodAnswer= new SimpleStringProperty();
    private IntegerProperty lifes=new SimpleIntegerProperty();
    private IntegerProperty score=new SimpleIntegerProperty();

    public String getWord() {
        return word.get();
    }

    public StringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getAnswerA() {
        return answerA.get();
    }

    public StringProperty answerAProperty() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA.set(answerA);
    }

    public String getAnswerB() {
        return answerB.get();
    }

    public StringProperty answerBProperty() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB.set(answerB);
    }

    public String getAnswerC() {
        return answerC.get();
    }

    public StringProperty answerCProperty() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC.set(answerC);
    }

    public String getGoodAnswer() {
        return goodAnswer.get();
    }

    public StringProperty goodAnswerProperty() {
        return goodAnswer;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer.set(goodAnswer);
    }

    public int getLifes() {
        return lifes.get();
    }

    public IntegerProperty lifesProperty() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes.set(lifes);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    @Override
    public String toString() {
        return getWord()+", \nanswerA "+getAnswerA()+", \nanswerB "+getAnswerB()+", \nanswerC "+getAnswerC()+", " +
                "\ngood answer "+getGoodAnswer();
    }
}