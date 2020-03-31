package me.stevenkin.boom.token;

public class Token {
    public static final Token END = new Token(null, -1, Type.BLANK) {
        @Override
        public String getOrigin() {
            throw new RuntimeException();
        }
    };
    private int lineNum;
    private String origin;
    private Type type;

    public Token(String origin, int lineNum, Type type) {
        this.origin = origin;
        this.lineNum = lineNum;
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public int getLineNum() {
        return lineNum;
    }

    enum Type {
        BLANK,
        OP,
        COMMA,
        POINT,
        SEMICOLON,
        BRACKETS,
        STRING,
        CHAR,
        NUMBER,
        IDENTIFIER
    }
}
