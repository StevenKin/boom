package me.stevenkin.boom.token;

public class SquareRToken extends Token {
    private final String squareR = "]";

    public SquareRToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return squareR;
    }
}
