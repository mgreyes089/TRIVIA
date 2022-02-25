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

    public int getTotalQuestions() {
        return this.questions.size();
    }

    public int getCorrectAnswers() {
        int correctAnswers = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.isAnswerCorrect()) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public double getPercentCorrectQuestionsByTopic(String topic) {
        int correctAnswers = getCorrectQuestionsByTopic(topic);
        int totalQuestions = getTotalQuestionsByTopic(topic);
        if (totalQuestions == 0) return 0;

        return (correctAnswers * 100) / totalQuestions;
    }

    public int getCorrectQuestionsByTopic(String topic) {
        int correctQuestionsByTopic = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.getTopic().equals(topic)) {
                if (currentQuestion.isAnswerCorrect()) {
                    correctQuestionsByTopic++;
                }
            }
        }
        return correctQuestionsByTopic;
    }

    public int getTotalQuestionsByTopic(String topic) {
        int questionsByTopic = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.getTopic().equals(topic)) {
                questionsByTopic++;
            }
        }
        return questionsByTopic;
    }

    public int getPointByTopic(String topic) {
        int pointsByTopic = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.getTopic().equals(topic)) {
                if (currentQuestion.isAnswerCorrect()) {
                    pointsByTopic += currentQuestion.getDifficulty();
                }
            }
        }
        return pointsByTopic;
    }
}
