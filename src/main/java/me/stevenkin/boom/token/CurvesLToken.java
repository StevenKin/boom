package me.stevenkin.boom.token;

public class CurvesLToken extends Token {
    private final String curvesL = "(";

    public CurvesLToken(int lineNum) {
        super(lineNum);
    }

    public Object getData() {
        return curvesL;
    }

}
