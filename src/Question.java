public class Question {
    private String question;
    private boolean trueOrFalse;

    public Question(String question, boolean trueOrFalse) {
        this.question = question;
        this.trueOrFalse = trueOrFalse;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isTrueOrFalse() {
        return trueOrFalse;
    }
}

