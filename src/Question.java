import java.util.ArrayList;

public class Question {
    private String statement;
    private int correctAnswer;
    private int userAnswer;
    private int difficulty;
    private String topic;
    private ArrayList<String> answers;

    public Question(String statement, int correctAnswer, int difficulty, String topic, ArrayList<String> answers) {
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.userAnswer = -1;
        this.difficulty = difficulty;
        this.topic = topic;
        this.answers = answers;
    }

    public String getStatement() {
        return statement;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public boolean isAnswerCorrect(){
        return correctAnswer == userAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getTopic() {
        return topic;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}
