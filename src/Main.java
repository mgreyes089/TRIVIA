import java.util.*;

public class Main {

    private static final int MAX_POINTS = 5;
    private static final String GEOGRAPHY = "geografia";
    private static final String SPORTS = "deportes";
    private static final String MOVIES = "cine";
    private static final String SERIES = "series";

    public static void main(String[] args) {
        LinkedList<Question> allQuestions = createQuestionList();
        Map<String, LinkedList<Question>> questions = createQuestionListByTopic(allQuestions);

        Player player1 = new Player(askUserName(1));
        Player player2 = new Player(askUserName(2));
        Player currentPlayer = player1;

        while (gameFinished(player1, player2, questions)) {
            printWhoPlays(currentPlayer);
            String usersChoice = askUserTopic(questions);
            System.out.println("===============");
            LinkedList<Question> topicQuestions = randomizeQuestions(questions.get(usersChoice));
            askQuestion(currentPlayer, topicQuestions);
            System.out.println("===============");
            currentPlayer = changePlayer(currentPlayer, player1, player2);
        }
        winnerPlayer(player1, player2);
        System.out.println("===============");
        printStatistics(player1);
        System.out.println("===============");
        printStatistics(player2);
    }

    private static void winnerPlayer(Player player1, Player player2) {
        if(player1.getPoints() >= player2.getPoints()){
            System.out.println(player1.getName().toUpperCase() + " es el ganador!");
        }else{
            System.out.println(player2.getName().toUpperCase() + " es el ganador!");
        }
    }

    private static void printWhoPlays(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " es tu turno.");
    }

    private static Player changePlayer(Player currentPlayer, Player player1, Player player2) {
        if(currentPlayer.equals(player1)){
            return player2;
        }
        return player1;
    }

    private static String askUserName(int playerNum) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre del jugador " + playerNum);
        return sc.nextLine();
    }

    private static void printStatistics(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + ", has acertado un total de " + currentPlayer.getCorrectAnswers() + " sobre " + currentPlayer.getTotalQuestions() + " preguntas.");
        System.out.println("Esto equivale a un " + ((currentPlayer.getCorrectAnswers() * 100)/currentPlayer.getTotalQuestions()) + "% de preguntas correctas.");
        System.out.println("Has obtenido un total de " + currentPlayer.getPoints() + " puntos.");
    }

    private static boolean gameFinished(Player player1, Player player2, Map<String, LinkedList<Question>> questions) {
        if ((player1.getPoints() >= MAX_POINTS || player2.getPoints() >= MAX_POINTS) || questions.values().stream().distinct().toList().get(0).isEmpty()) {
            return false;
        }
        return true;
    }

    private static String askUserTopic(Map<String, LinkedList<Question>> questions) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printAvailableTopics(questions);

            System.out.println("Escribe la temática con la que quieres jugar");
            String choosenTopic = sc.nextLine().toLowerCase();
            if (!questions.containsKey(choosenTopic)) {
                System.out.println("La temática no existe, vuelve a intentarlo");
            }
            return choosenTopic;
        }
    }

    private static void printAvailableTopics(Map<String, LinkedList<Question>> questions) {
        System.out.println("Las temáticas disponibles son:");
        for (String key : questions.keySet()) {
            if (!questions.get(key).isEmpty()) {
                System.out.println(key);
            }
        }
    }

    private static void askQuestion(Player currentPlayer, LinkedList<Question> questions) {
        Question currentQuestion = questions.get(0);
        System.out.println(currentQuestion.getStatement());
        boolean userTrueOrFalse = getUserAnswer();
        currentQuestion.setUserAnswer(userTrueOrFalse);
        currentPlayer.setQuestion(currentQuestion);

        isAnswerCorrect(currentPlayer, currentQuestion);
        questions.remove(0);
    }

    private static boolean getUserAnswer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Contesta True/False (T/F)");
        String answer = sc.nextLine();
        return answer.equalsIgnoreCase("T");
    }

    private static void isAnswerCorrect(Player currentPlayer, Question currentQuestion) {
        if (currentQuestion.isAnswerCorrect()) {
            System.out.println("Has acertado!!!");
            currentPlayer.setPoints(currentQuestion.getDifficulty());
            System.out.println("Has obtenido " + currentQuestion.getDifficulty() + " puntos.");
        } else {
            System.out.println("Nice try, pero no chaval ;-)");
        }
    }

    private static Map<String, LinkedList<Question>> createQuestionListByTopic(LinkedList<Question> allQuestions) {
        Map<String, LinkedList<Question>> questions = new HashMap<>();
        questions.put(GEOGRAPHY, getAllQuestionsByTopic(allQuestions, GEOGRAPHY));
        questions.put(SPORTS   , getAllQuestionsByTopic(allQuestions, SPORTS   ));
        questions.put(MOVIES   , getAllQuestionsByTopic(allQuestions, MOVIES   ));
        questions.put(SERIES   , getAllQuestionsByTopic(allQuestions, SERIES   ));
        return questions;
    }

    private static LinkedList<Question> randomizeQuestions(LinkedList<Question> allQuestionsByTopic) {
        Collections.shuffle(allQuestionsByTopic);
        return allQuestionsByTopic;
    }

    private static LinkedList<Question> getAllQuestionsByTopic(LinkedList<Question> allQuestions, String topic) {
        LinkedList<Question> questions = new LinkedList<>();
        for(Question currentQuestion : allQuestions){
            if(currentQuestion.getTopic().equalsIgnoreCase(topic)){
                questions.add(currentQuestion);
            }
        }
        return questions;
    }

    private static LinkedList<Question> createQuestionList() {
        LinkedList<Question> questions = new LinkedList<>();

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
}
