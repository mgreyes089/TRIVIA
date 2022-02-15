public class Question {

    private String statement;
    private boolean trueOrFalse;

    public Question(String statement, boolean trueOrFalse) {
        this.statement = statement;
        this.trueOrFalse = trueOrFalse;
    }

    public String getStatement() {
        return statement;
    }

    public boolean isTrueOrFalse() {
        return trueOrFalse;
    }
}