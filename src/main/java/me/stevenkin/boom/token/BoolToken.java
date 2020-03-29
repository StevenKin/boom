package me.stevenkin.boom.token;

public class BoolToken extends Token {
    private Boolean bool;

    public BoolToken(int lineNum, Boolean bool) {
        super(lineNum);
        this.bool = bool;
    }

    public Object getData() {
        return bool;
    }
}
