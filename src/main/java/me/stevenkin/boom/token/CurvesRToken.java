package me.stevenkin.boom.token;

public class CurvesRToken extends Token {
    private final String curvesR = "(";

    public CurvesRToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return curvesR;
    }
}
