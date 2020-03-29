package me.stevenkin.boom.token;

public class StringToken extends Token {
    private String string;

    public StringToken(int lineNum, String string) {
        super(lineNum);
        this.string = string;
    }

    public Object getData() {
        return string;
    }
}
