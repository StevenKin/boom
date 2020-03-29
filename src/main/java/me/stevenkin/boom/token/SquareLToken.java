package me.stevenkin.boom.token;

public class SquareLToken extends Token {
    private final String squareL = "[";

    public SquareLToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return squareL;
    }
}
