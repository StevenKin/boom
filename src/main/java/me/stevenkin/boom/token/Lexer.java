package me.stevenkin.boom.token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private LineNumReader reader;
    private List<Token> tokens;

    public Lexer(LineNumReader reader) {
        this.reader = reader;
        this.tokens = new ArrayList<>();
    }

    public Token read() {
        if (fillTokens(0))
            return tokens.remove(0);
        return Token.END;
    }

    public Token peek(int i) {
        if (fillTokens(i))
            return tokens.get(i);
        return Token.END;
    }

    private boolean fillTokens(int i) {
        while (i >= tokens.size()) {
           if (!addTokens(reader.readLine(), reader.getCurrLineNum()))
               return false;
        }
        return true;
    }

    private boolean addTokens(String line, int currLineNum) {
        if (line == null)
            return false;
        if ("".equals(line))
            return true;
        State state = State.BLANK;
        StringBuilder buffer = new StringBuilder();
        boolean isDecimal = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            switch (state) {
                case BLANK:
                    if (isBlank(c))
                        continue;
                    if (isNum(c) || isComma(c)) {
                        if (isComma(c))
                            isDecimal = true;
                        buffer.append(c);
                        state = State.NUMBER;
                    }
                    break;
                case NUMBER:

                    if (isDecimal) {
                        if (!isNum(c))
                            throw new RuntimeException();
                        buffer.append(c);
                        continue;
                    }
                    if (isComma(c)) {
                        isDecimal = true;
                    }
                    buffer.append(c);
                    break;
            }
        }
        return true;
    }

    private boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }
    private boolean isBraceL(char c) {
        return c == '{';
    }

    private boolean isBraceR(char c) {
        return c == '}';
    }

    private boolean isCurvesL(char c) {
        return c == '(';
    }

    private boolean isCurvesR(char c) {
        return c == ')';
    }

    private boolean isSquareL(char c) {
        return c == '[';
    }

    private boolean isSquareR(char c) {
        return c == ']';
    }

    private boolean isOperator(char c) {
        return c == '='
                || c == '>'
                || c == '<'
                || c == '+'
                || c == '-'
                || c == '*'
                || c == '/'
                || c == '%'
                || c == '|'
                || c == '&'
                || c == '!'
                || c == '^'
                || c == ':'
                || c == '?';
    }

    private boolean isComma(char c) {
        return c == '.';
    }

    private boolean isSemicolon(char c) {
        return c == ';';
    }

    private boolean isDqm(char c) {
        return c == '"';
    }

    private boolean isSqm(char c) {
        return c == '\'';
    }

    private boolean isBlank(char c) {
        return c == '\n'
                || c == '\t'
                || c == ' ';
    }

    enum State {
        BLANK,
        NUMBER
    }
}
