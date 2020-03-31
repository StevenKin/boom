package me.stevenkin.boom.token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private LineNumReader reader;
    private StateMachine stateMachine;
    private List<Token> tokens;

    public Lexer(LineNumReader reader) {
        this.reader = reader;
        this.stateMachine = new StateMachine(reader);
        this.tokens = new ArrayList<>();
    }

    public Token read() {
        fillTokens(0);
        return tokens.remove(0);
    }

    public Token peek(int i) {
        fillTokens(i);
        return tokens.get(i);
    }

    private void fillTokens(int i) {
        while (i >= tokens.size()) {
            tokens.add(stateMachine.next());
        }
    }
}
