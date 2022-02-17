import java.util.*;

public class Main {

    private static int MAX_POINTS = 20;
    private static String GEOGRAPHY = "geografia";
    private static String SPORTS = "deportes";
    private static String MOVIES = "cine";
    private static String SERIES = "series";

    public static void main(String[] args) {
        Map<String, List<Question>> questions = buildQuestionList();

        boolean continuePlaying = true;
        while(continuePlaying) {
            String usersChoice = askUserTopic(questions);

            List<Question> topicQuestions = questions.get(usersChoice);
            askTheQuestions(topicQuestions);

            int correctAnswers = getCorrectAnswers(questions);
            int totalScore = getTotalScore(questions);

            //TODO printAnswers no es pot utilitzar degut al enunciat de la fase 3
            printAnswers(questions);
            System.out.println("Has acertado un total de " + correctAnswers + " sobre " + questions.size() + " preguntas");
            System.out.println("Has obtenido un total de " + totalScore + " puntos");
            continuePlaying = gameFinished(questions);
        }
    }

    private static boolean gameFinished(Map<String, List<Question>> questions) {
        if(getTotalScore(questions) >= MAX_POINTS){
            return true;
        }
    }

    private static String askUserTopic(Map<String, List<Question>> questions) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Las temáticas disponibles son:");
            for (String key : questions.keySet()) {
                if(!questions.get(key).isEmpty()){
                    System.out.println(key);
                }
            }
            System.out.println("Escribe la temática con la que quieres jugar");
            String choosenTopic = sc.nextLine().toLowerCase();
            if (!questions.containsKey(choosenTopic)) {
                System.out.println("La temática no existe, vuelve a intentarlo");
            }
            return choosenTopic;
        }
    }

    private static void printAnswers(List<Question> questions) {
        for (Question currentQuestion : questions) {
            System.out.println("Para la pregunta: " + currentQuestion.getStatement());
            System.out.println("La respuesta era: " + currentQuestion.isCorrectAnswer());
            System.out.println("Y tu has responidido: " + currentQuestion.isUserAnswer());
            System.out.println("");
        }
    }

    private static int getTotalScore(Map<String, List<Question>> questions) {
        int totalScore = 0;
        for (String topic : questions.keySet()) {
            List<Question> questionList = questions.get(topic);
            for (Question currentQuestion : questionList) {
                if (currentQuestion.isAnswerCorrect()) {
                    totalScore += currentQuestion.getDifficulty();
                }
            }
        }
        return totalScore;
    }


    private static int getCorrectAnswers(Map<String, List<Question>> questions) {
        int correctAnswers = 0;
        for (String topic : questions.keySet()) {
            List<Question> questionList = questions.get(topic);
            for (Question currentQuestion : questionList) {
                if (currentQuestion.isCorrectAnswer()) {
                    correctAnswers++;
                }
            }
        }
        return correctAnswers;
    }

    private static void askTheQuestions(List<Question> questions) {
        Scanner sc = new Scanner(System.in);
        Question currentQuestion = questions.get(0);

        System.out.println(currentQuestion.getStatement());
        System.out.println("Contesta True/False (T/F)");
        String text = sc.nextLine();

        boolean userTrueOrFalse = text.equalsIgnoreCase("T");
        currentQuestion.setUserAnswer(userTrueOrFalse);

        if (currentQuestion.isAnswerCorrect()) {
            System.out.println("Has acertado!!!");
        } else {
            System.out.println("Nice try, pero no chaval ;-)");
        }
        questions.remove(0);
    }

    private static Map<String, List<Question>> buildQuestionList() {
        Map<String, List<Question>> questions = new HashMap<>();
        List<Question> geo = new ArrayList<>();
        List<Question> sports = new ArrayList<>();
        List<Question> movies = new ArrayList<>();
        List<Question> series = new ArrayList<>();

        geo.add(new Question("La capital de Francia es Paris", true, 3, "Geografia"));
        geo.add(new Question("La capital de Italia es Roma", true, 2, "Geografia"));
        geo.add(new Question("La capital de Portugal es Lisboa", true, 4, "Geografia"));
        geo.add(new Question("La capital de Alemania es Londres", false, 5, "Geografia"));
        geo.add(new Question("La capital de Holanda es Bruselas", false, 5, "Geografia"));

        sports.add(new Question("El Barça viste de azul y rojo", true, 2, "Deportes"));
        sports.add(new Question("El Madrid viste de blanco y rojo", false, 2, "Deportes"));
        sports.add(new Question("El Espanyol viste de rojo y amarillo", false, 2, "Deportes"));
        sports.add(new Question("Tom Brady ha ganado 7 veces la SuperBowl", true, 5, "Deportes"));
        sports.add(new Question("Valentino Rossi lleva el número 46", true, 3, "Deportes"));

        movies.add(new Question("Titanic va sobre un barco que se hunde", true, 2, "Cine"));
        movies.add(new Question("La Loca Academia de Policía es de humor", true, 3, "Cine"));
        movies.add(new Question("El Jóker es el compañero de Batman", false, 3, "Cine"));
        movies.add(new Question("El trabajo de Spiderman es periodista", true, 4, "Cine"));
        movies.add(new Question("Shrek es de color granate", false, 2, "Cine"));

        series.add(new Question("Steve Carrell es el protagonista de The Office", true, 4, "Series"));
        series.add(new Question("The Big Bang Theory va sobre geólogos", false, 3, "Series"));
        series.add(new Question("En Dos Hombres y Medio los protagonistas son 3 hombres y uno de ellos es acondroplásico", false, 4, "Series"));
        series.add(new Question("Penny de The Big Bang Theory nació en Nebraska", true, 5, "Series"));
        series.add(new Question("En Dos Chicas Sin Blanca trabajaban en una cafetería", true, 5, "Series"));

        questions.put(GEOGRAPHY, geo);
        questions.put(SPORTS, sports);
        questions.put(MOVIES, movies);
        questions.put(SERIES, series);

        return questions;
    }
}
