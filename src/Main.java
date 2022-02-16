import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Question> questions = buildQuestionList();

        askTheQuestions(questions);
        int correctAnswers = getCorrectAnswers(questions);
        int totalScore = getTotalScore(questions);
        printAnswers(questions);

        System.out.println("Has acertado un total de " + correctAnswers + " sobre " + questions.size() + " preguntas");
        System.out.println("Has obtenido un total de " + totalScore + " puntos");
    }

    private static void printAnswers(List<Question> questions) {
        for (Question currentQuestion : questions) {
            System.out.println("Para la pregunta: " + currentQuestion.getStatement());
            System.out.println("La respuesta era: " + currentQuestion.isCorrectAnswer());
            System.out.println("Y tu has responidido: " + currentQuestion.isUserAnswer());
            System.out.println("");
        }
    }

    private static int getTotalScore(List<Question> questions) {
        int totalScore = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.isAnswerCorrect()) {
                totalScore += currentQuestion.getDifficulty();
            }
        }
        return totalScore;
    }


    private static int getCorrectAnswers(List<Question> questions) {
        int correctAnswers = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.isCorrectAnswer()) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private static void askTheQuestions(List<Question> questions) {
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);

            System.out.println(currentQuestion.getStatement());
            System.out.println("Contesta True/False (T/F)");
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();

            boolean userTrueOrFalse = text.equalsIgnoreCase("T");
            currentQuestion.setUserAnswer(userTrueOrFalse);

            if (currentQuestion.isAnswerCorrect()) {
                System.out.println("Has acertado!!!");
            } else {
                System.out.println("Nice try, pero no chaval ;-)");
            }
        }
    }

    private static List<Question> buildQuestionList() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("La capital de Francia es Paris", true, 3));
        questions.add(new Question("La capital de Italia es Roma", true, 2));
        questions.add(new Question("La capital de Portugal es Lisboa", true, 4));
        questions.add(new Question("La capital de Alemania es Londres", false, 5));
        questions.add(new Question("La capital de Holanda es Bruselas", false, 5));

        return questions;
    }
}
