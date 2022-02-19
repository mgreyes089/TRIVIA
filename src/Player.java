import java.util.ArrayList;

public class Player {
    private String name;
    private int points;
    private ArrayList<Question> questions = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestion(Question question) {
        this.questions.add(question);
    }

    public int getTotalQuestions(){
        return this.questions.size();
    }

    public int getCorrectAnswers(){
        int correctAnswers = 0;
        for (Question currentQuestion: questions) {
            if(currentQuestion.isAnswerCorrect()){
                correctAnswers++;
            }
        }
        return correctAnswers;
    }
}
