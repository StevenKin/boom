package me.stevenkin.boom.token;

public abstract class Token {
    public static final Token END = new Token(-1) {
        @Override
        public Object getData() {
            throw new RuntimeException();
        }
    };

    private int lineNum;

    public Token(int lineNum) {
        this.lineNum = lineNum;
    }

    public abstract Object getData();

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
