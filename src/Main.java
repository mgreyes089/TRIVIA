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

        int numberOfPlayers = askHowManyPlayers();
        List<Player> players = createPlayers(numberOfPlayers);

        int currentPlayer = 0;

        while (!gameFinished(players, questions)) {
            printWhoPlays(players, currentPlayer);
            String usersChoice = askUserTopic(questions);
            System.out.println("===============");
            LinkedList<Question> topicQuestions = randomizeQuestions(questions.get(usersChoice));
            askQuestion(players, currentPlayer, topicQuestions);
            System.out.println("===============");
            currentPlayer = changePlayer(players, currentPlayer);
        }
        winnerPlayer(players);
        System.out.println("===============");
        printStatistics(players, questions);
        System.out.println("===============");
        getPlayerRanking(players);
    }

    private static void getPlayerRanking(List<Player> players) {
        List<Player> winner = players.stream()
                .sorted(Comparator.comparing(Player::getPoints)
                        .reversed())
                .toList();

        System.out.println("Ranking por puntos:");
        for (int i = 0; i < winner.size(); i++) {
            System.out.println((i + 1) + "- " + winner.get(i).getName() + " (" + winner.get(i).getPoints() + " puntos)");
        }
    }

    private static List<Player> createPlayers(int numberOfPlayers) {
        Scanner sc = new Scanner(System.in);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Introduce el nombre del jugador " + (i + 1));
            players.add(new Player(sc.nextLine()));
        }
        return players;
    }

    private static int askHowManyPlayers() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el número de jugadores");
        return sc.nextInt();
    }

    private static void winnerPlayer(List<Player> players) {
        Player winner = players.stream()
                .max(Comparator.comparing(Player::getPoints))
                .get();
        System.out.println(winner.getName().toUpperCase() + " es el/la ganador/a!");
    }

    private static void printWhoPlays(List<Player> players, int currentPlayer) {
        System.out.println(players.get(currentPlayer).getName() + " es tu turno.");
    }

    private static int changePlayer(List<Player> players, int currentPlayer) {
        if (currentPlayer < players.size() - 1) {
            return currentPlayer + 1;
        }
        return 0;
    }

    private static void printStatistics(List<Player> players, Map<String, LinkedList<Question>> questions) {
        System.out.println("ESTADÍSTICAS");
        for (Player currentPlayer : players) {
            System.out.println(currentPlayer.getName().toUpperCase() + ":");
            for (String key : questions.keySet()) {
                System.out.println("Para la temática " + key.toUpperCase() + " has obtenido un " + currentPlayer.getPercentCorrectQuestionsByTopic(key) + "% de aciertos y " + currentPlayer.getPointByTopic(key) + " puntos");
            }
            System.out.println("En total has obtenido un total de " + currentPlayer.getPoints() + " puntos.");
        }
    }

    private static boolean gameFinished(List<Player> players, Map<String, LinkedList<Question>> questionsMap) {

        if (isMapEmpty(questionsMap)){
            return true;
        }
        /*
        if (questions.values().stream().distinct().toList().get(0).isEmpty()) {
            return false;
        }
        */
        if (playerHasWon(players)) return true;
        return false;
    }

    private static boolean playerHasWon(List<Player> players) {
        for (Player currentPlayer : players) {
            if (currentPlayer.getPoints() >= MAX_POINTS) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMapEmpty(Map<String, LinkedList<Question>> questionsMap) {
        return questionsMap.values().stream().allMatch(questionList -> questionList.isEmpty());
    }

    private static String askUserTopic(Map<String, LinkedList<Question>> questions) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printAvailableTopics(questions);
            System.out.println("Escribe la temática con la que quieres jugar");
            String choosenTopic = sc.nextLine().toLowerCase();
            if (!questions.containsKey(choosenTopic)) {
                System.out.println("La temática no existe, vuelve a intentarlo");
            } else {
                return choosenTopic;
            }
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

    private static void askQuestion(List<Player> players, int currentPlayer, LinkedList<Question> questions) {
        Question currentQuestion = questions.pollFirst();
        printQuestionAndAnswers(currentQuestion);
        int userAnswer = getUserAnswer();
        currentQuestion.setUserAnswer(userAnswer);
        Player player = players.get(currentPlayer);
        isAnswerCorrect(player, currentQuestion);
        player.setQuestion(currentQuestion);
    }

    private static void printQuestionAndAnswers(Question currentQuestion) {
        System.out.println(currentQuestion.getStatement());
        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            System.out.println((i + 1) + "- " + currentQuestion.getAnswers().get(i));
        }
    }

    private static int getUserAnswer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe el número de la respuesta correcta");
        return sc.nextInt() - 1;
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
        questions.put(SPORTS, getAllQuestionsByTopic(allQuestions, SPORTS));
        questions.put(MOVIES, getAllQuestionsByTopic(allQuestions, MOVIES));
        questions.put(SERIES, getAllQuestionsByTopic(allQuestions, SERIES));
        return questions;
    }

    private static LinkedList<Question> randomizeQuestions(LinkedList<Question> allQuestionsByTopic) {
        Collections.shuffle(allQuestionsByTopic);
        return allQuestionsByTopic;
    }

    private static LinkedList<Question> getAllQuestionsByTopic(LinkedList<Question> allQuestions, String topic) {
        LinkedList<Question> questions = new LinkedList<>();
        for (Question currentQuestion : allQuestions) {
            if (currentQuestion.getTopic().equalsIgnoreCase(topic)) {
                questions.add(currentQuestion);
            }
        }
        return questions;
    }

    private static LinkedList<Question> createQuestionList() {
        LinkedList<Question> questions = new LinkedList<>();

        questions.add(new Question("¿Cuál es la capital de Francia?", 1, 3, GEOGRAPHY, new ArrayList<>(Arrays.asList("Lyon", "París", "Nantes", "Toulouse"))));
        questions.add(new Question("¿Cuál es la capital de Italia?", 0, 2, GEOGRAPHY, new ArrayList<>(Arrays.asList("Roma", "Nápoles", "Tavullia", "Milán"))));
        questions.add(new Question("¿Cuál es la capital de Portugal?", 3, 4, GEOGRAPHY, new ArrayList<>(Arrays.asList("Faro", "Oporto", "Coímbra", "Lisboa"))));
        questions.add(new Question("¿Cuál es la capital de Alemania?", 1, 5, GEOGRAPHY, new ArrayList<>(Arrays.asList("Düsseldorf", "Berlín", "Stuttgart", "Múnich"))));
        questions.add(new Question("¿Cuál es la capital de Holanda?", 2, 5, GEOGRAPHY, new ArrayList<>(Arrays.asList("Eindhoven", "Groninga", "Ámsterdam", "Alkmaar"))));

        questions.add(new Question("¿Cuáles són los colores del Barça?", 1, 2, SPORTS, new ArrayList<>(Arrays.asList("Amarillo", "Azul y rojo", "Azul y blanco", "Rojo y blanco"))));
        questions.add(new Question("¿Cual es el color del Madrid?", 2, 2, SPORTS, new ArrayList<>(Arrays.asList("Azul", "Amarillo", "Blanco", "Rojo"))));
        questions.add(new Question("¿Cuáles són los colores del Espanyol?", 3, 2, SPORTS, new ArrayList<>(Arrays.asList("Blanco y rojo", "Azul", "Blanco", "Blanco y azul"))));
        questions.add(new Question("¿Cuántas SuperBowl ha ganado Tom Brady?", 3, 5, SPORTS, new ArrayList<>(Arrays.asList("1", "4", "6", "7"))));
        questions.add(new Question("¿Cual es el número de Valentino Rossi?", 1, 3, SPORTS, new ArrayList<>(Arrays.asList("93", "46", "33", "15"))));

        questions.add(new Question("¿En qué país nació Bud Spencer?", 1, 2, MOVIES, new ArrayList<>(Arrays.asList("Estados Unidos", "Italia", "Gran Bretaña", "Escocia"))));
        questions.add(new Question("¿De qué género es La Loca Academia de Policía?", 3, 3, MOVIES, new ArrayList<>(Arrays.asList("Documental", "Acción", "Suspense", "Comedia"))));
        questions.add(new Question("¿Quién es el compañero de Batman?", 1, 3, MOVIES, new ArrayList<>(Arrays.asList("Catwoman", "Robin", "Jóker", "Enigma"))));
        questions.add(new Question("¿De qué trabaja Spiderman?", 2, 4, MOVIES, new ArrayList<>(Arrays.asList("Repartidor", "Policía", "Periodista", "Barrendero"))));
        questions.add(new Question("¿De qué color es Shrek?", 1, 2, MOVIES, new ArrayList<>(Arrays.asList("Amarillo", "Verde", "Rojo", "Azul"))));

        questions.add(new Question("¿Quién es el protagonista de The Office?", 3, 4, SERIES, new ArrayList<>(Arrays.asList("Jenna Fischer", "Rainn Wilson", "John Krasinski", "Steve Carell"))));
        questions.add(new Question("¿De qué va The Big Bang Theory?", 1, 3, SERIES, new ArrayList<>(Arrays.asList("Geólogos", "Físicos", "Entomólogos ", "Ictiólogos"))));
        questions.add(new Question("En Dos Hombres y Medio, ¿quién es el padre de Jake?", 0, 4, SERIES, new ArrayList<>(Arrays.asList("Alan", "Walden", "Charlie", "Herb"))));
        questions.add(new Question("¿Dónde hació Penny de The Big Bang Theory?", 2, 5, SERIES, new ArrayList<>(Arrays.asList("Texas", "Kansas", "Omaha", "Hawaii"))));
        questions.add(new Question("¿Dónde trabajan las protagonistas de Dos Chicas Sin Blanca", 0, 5, SERIES, new ArrayList<>(Arrays.asList("Cafetería", "Discoteca", "Escuela", "Tienda"))));

        return questions;
    }
}
