package me.stevenkin.boom.token;

public class BraceLToken extends Token {
    private final String braceL = "{";

    public BraceLToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return braceL;
    }
}
