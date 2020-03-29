package me.stevenkin.boom.token;

public class IdentifierToken extends Token {
    private String identifier;

    public IdentifierToken(int lineNum, String identifier) {
        super(lineNum);
        this.identifier = identifier;
    }

    public Object getData() {
        return identifier;
    }
}
