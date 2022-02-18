import java.util.*;

public class Main {

    private static final int MAX_POINTS = 20;
    private static final String GEOGRAPHY = "geografia";
    private static final String SPORTS = "deportes";
    private static final String MOVIES = "cine";
    private static final String SERIES = "series";

    private static int userPoints = 0;

    public static void main(String[] args) {
        List<Question> allQuestions = createQuestionList();
        Map<String, List<Question>> questions = createQuestionListByTopic(allQuestions);
        //Map<String, List<Question>> questions = buildQuestionList();

        while (gameFinished(questions)) {
            String usersChoice = askUserTopic(questions);

            List<Question> topicQuestions = questions.get(usersChoice);
            askQuestion(topicQuestions);

            int correctAnswers = getCorrectAnswers(allQuestions);
            int totalScore = getTotalScore(questions);

            //TODO printAnswers no es pot utilitzar degut al enunciat de la fase 3
            //printAnswers(questions);
            System.out.println("Has acertado un total de " + correctAnswers + " sobre " + allQuestions.size() + " preguntas");
            System.out.println("Has obtenido un total de " + userPoints + " puntos");

        }
    }

    private static boolean gameFinished(Map<String, List<Question>> questions) {
        if (userPoints >= MAX_POINTS) {
            return false;
        }
        return true;
    }

    private static String askUserTopic(Map<String, List<Question>> questions) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Las temáticas disponibles son:");
            for (String key : questions.keySet()) {
                if (!questions.get(key).isEmpty()) {
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

    private static int getCorrectAnswers(List<Question> questions) {
        int correctAnswers = 0;
        for (Question currentQuestion : questions) {
            if (currentQuestion.isCorrectAnswer()) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private static void askQuestion(List<Question> questions) {
        Scanner sc = new Scanner(System.in);
        Question currentQuestion = questions.get(0);

        System.out.println(currentQuestion.getStatement());
        System.out.println("Contesta True/False (T/F)");
        String text = sc.nextLine();

        boolean userTrueOrFalse = text.equalsIgnoreCase("T");
        currentQuestion.setUserAnswer(userTrueOrFalse);

        if (currentQuestion.isAnswerCorrect()) {
            System.out.println("Has acertado!!!");
            userPoints += currentQuestion.getDifficulty();
        } else {
            System.out.println("Nice try, pero no chaval ;-)");
        }
        questions.remove(0);
    }

    private static Map<String, List<Question>> createQuestionListByTopic(List<Question> allQuestions) {
        Map<String, List<Question>> questions = new HashMap<>();
        questions.put(GEOGRAPHY, getAllQuestionsByTopic(allQuestions, GEOGRAPHY));
        questions.put(SPORTS,    getAllQuestionsByTopic(allQuestions, SPORTS));
        questions.put(MOVIES,    getAllQuestionsByTopic(allQuestions, MOVIES));
        questions.put(SERIES,    getAllQuestionsByTopic(allQuestions, SERIES));
        return questions;
    }

    private static List<Question> getAllQuestionsByTopic(List<Question> allQuestions, String topic) {
        List<Question> questions = new ArrayList<>();
        for(Question currentQuestion : allQuestions){
            if(currentQuestion.getTopic().equalsIgnoreCase(topic)){
                questions.add(currentQuestion);
            }
        }
        return questions;
    }

    private static List<Question> createQuestionList() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("La capital de Francia es Paris", true, 3, GEOGRAPHY));
        questions.add(new Question("La capital de Italia es Roma", true, 2, GEOGRAPHY));
        questions.add(new Question("La capital de Portugal es Lisboa", true, 4, GEOGRAPHY));
        questions.add(new Question("La capital de Alemania es Londres", false, 5, GEOGRAPHY));
        questions.add(new Question("La capital de Holanda es Bruselas", false, 5, GEOGRAPHY));

        questions.add(new Question("El Barça viste de azul y rojo", true, 2, SPORTS));
        questions.add(new Question("El Madrid viste de blanco y rojo", false, 2, SPORTS));
        questions.add(new Question("El Espanyol viste de rojo y amarillo", false, 2, SPORTS));
        questions.add(new Question("Tom Brady ha ganado 7 veces la SuperBowl", true, 5, SPORTS));
        questions.add(new Question("Valentino Rossi lleva el número 46", true, 3, SPORTS));

        questions.add(new Question("Titanic va sobre un barco que se hunde", true, 2, MOVIES));
        questions.add(new Question("La Loca Academia de Policía es de humor", true, 3, MOVIES));
        questions.add(new Question("El Jóker es el compañero de Batman", false, 3, MOVIES));
        questions.add(new Question("El trabajo de Spiderman es periodista", true, 4, MOVIES));
        questions.add(new Question("Shrek es de color granate", false, 2, MOVIES));

        questions.add(new Question("Steve Carrell es el protagonista de The Office", true, 4, SERIES));
        questions.add(new Question("The Big Bang Theory va sobre geólogos", false, 3, SERIES));
        questions.add(new Question("En Dos Hombres y Medio los protagonistas son 3 hombres y uno de ellos es acondroplásico", false, 4, SERIES));
        questions.add(new Question("Penny de The Big Bang Theory nació en Nebraska", true, 5, SERIES));
        questions.add(new Question("En Dos Chicas Sin Blanca trabajaban en una cafetería", true, 5, SERIES));

        return questions;
    }

    private static Map<String, List<Question>> buildQuestionList() {
        Map<String, List<Question>> questions = new HashMap<>();
        List<Question> geo = new ArrayList<>();
        List<Question> sports = new ArrayList<>();
        List<Question> movies = new ArrayList<>();
        List<Question> series = new ArrayList<>();

        geo.add(new Question("La capital de Francia es Paris", true, 3, GEOGRAPHY));
        geo.add(new Question("La capital de Italia es Roma", true, 2, GEOGRAPHY));
        geo.add(new Question("La capital de Portugal es Lisboa", true, 4, GEOGRAPHY));
        geo.add(new Question("La capital de Alemania es Londres", false, 5, GEOGRAPHY));
        geo.add(new Question("La capital de Holanda es Bruselas", false, 5, GEOGRAPHY));

        sports.add(new Question("El Barça viste de azul y rojo", true, 2, SPORTS));
        sports.add(new Question("El Madrid viste de blanco y rojo", false, 2, SPORTS));
        sports.add(new Question("El Espanyol viste de rojo y amarillo", false, 2, SPORTS));
        sports.add(new Question("Tom Brady ha ganado 7 veces la SuperBowl", true, 5, SPORTS));
        sports.add(new Question("Valentino Rossi lleva el número 46", true, 3, SPORTS));

        movies.add(new Question("Titanic va sobre un barco que se hunde", true, 2, MOVIES));
        movies.add(new Question("La Loca Academia de Policía es de humor", true, 3, MOVIES));
        movies.add(new Question("El Jóker es el compañero de Batman", false, 3, MOVIES));
        movies.add(new Question("El trabajo de Spiderman es periodista", true, 4, MOVIES));
        movies.add(new Question("Shrek es de color granate", false, 2, MOVIES));

        series.add(new Question("Steve Carrell es el protagonista de The Office", true, 4, SERIES));
        series.add(new Question("The Big Bang Theory va sobre geólogos", false, 3, SERIES));
        series.add(new Question("En Dos Hombres y Medio los protagonistas son 3 hombres y uno de ellos es acondroplásico", false, 4, SERIES));
        series.add(new Question("Penny de The Big Bang Theory nació en Nebraska", true, 5, SERIES));
        series.add(new Question("En Dos Chicas Sin Blanca trabajaban en una cafetería", true, 5, SERIES));

        questions.put(GEOGRAPHY, geo);
        questions.put(SPORTS, sports);
        questions.put(MOVIES, movies);
        questions.put(SERIES, series);

        return questions;
    }
}
