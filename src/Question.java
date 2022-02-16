public class Question {
    private String statement;
    private boolean correctAnswer;
    private boolean userAnswer;
    private int difficulty;

    public Question(String statement, boolean correctAnswer, int difficulty) {
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.userAnswer = false;
        this.difficulty = difficulty;
    }

    public String getStatement() {
        return statement;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setUserAnswer(boolean userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isUserAnswer() {
        return userAnswer;
    }

    public boolean isAnswerCorrect(){
        return correctAnswer == userAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
