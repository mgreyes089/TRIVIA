import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Question> questions = buildQuestionList();
        int counter = askTheQuestions(questions);
        System.out.println("El número total de respuestas correctas es: " + counter + ". Numero total de preguntas: " + questions.size());
    }

    private static int askTheQuestions(List<Question> questions) {

        int counterRightAnwsers = 0;

        for (int i = 0; i < questions.size(); i++) {

            Question currentQuestion = questions.get(i);

            System.out.println(currentQuestion.getStatement());

            System.out.println("Contesta True/False (T/F)");
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();

            boolean userTrueOrFalse = text.equalsIgnoreCase("T");

            if (userTrueOrFalse == currentQuestion.isTrueOrFalse()) {
                counterRightAnwsers++;
                System.out.println("Has acertado!!!");
            }
            else {
                System.out.println("Nice try, pero no chaval ;-)");
            }
        }

        return counterRightAnwsers;
    }

    private static List<Question> buildQuestionList() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("La capital de Francia es Paris", true));
        questions.add(new Question("La capital de Italia es Roma", true));
        questions.add(new Question("La capital de Portugal es Lisboa", true));
        questions.add(new Question("La capital de Alemania es Londres", false));
        questions.add(new Question("La capital de Holanda es Bruselas", false));

        return questions;
    }
}
