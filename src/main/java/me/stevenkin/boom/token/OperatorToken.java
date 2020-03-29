package me.stevenkin.boom.token;

public class OperatorToken extends Token {
    private String op;

    public OperatorToken(int lineNum, String op) {
        super(lineNum);
        this.op = op;
    }

    public Object getData() {
        return op;
    }
}
