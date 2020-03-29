package me.stevenkin.boom.token;

public class NumberToken extends Token{
    private Number num;

    public NumberToken(int lineNum, Number num) {
        super(lineNum);
        this.num = num;
    }

    public Object getData() {
        return num;
    }
}
