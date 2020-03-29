package me.stevenkin.boom.token;

public class SemicolonToken extends Token {
    public SemicolonToken(int lineNum) {
        super(lineNum);
    }

    @Override
    public Object getData() {
        return ";";
    }
}
