package pl.app.flashcards.database.models;

public class Learning {
    private String word;
    private String answerA;
    private String answerB;
    private String answerC;
    private String goodAnswer;
    private int lifes;
    private int score;

    public Learning() {
    }


    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }
    public String toString(){
       return getWord()+", \nanswerA "+getAnswerA()+", \nanswerB "+getAnswerB()+", \nanswerC "+getAnswerC()+", " +
                "\ngood answer "+getGoodAnswer();
    }
}
