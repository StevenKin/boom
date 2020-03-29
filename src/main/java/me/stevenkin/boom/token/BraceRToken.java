package me.stevenkin.boom.token;

public class BraceRToken extends Token {
    private final String braceR = "}";

    public BraceRToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return braceR;
    }
}
