package me.stevenkin.boom;

import me.stevenkin.boom.token.Lexer;
import me.stevenkin.boom.token.LineNumReader;
import me.stevenkin.boom.token.Token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LexerTest {
    public static void main(String[] args) {
        InputStream input = LexerTest.class.getClassLoader().getResourceAsStream("test.boom");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Lexer lexer = new Lexer(new LineNumReader(reader));
        Token token;
        while ((token = lexer.read()) != Token.END) {
            System.out.println(token.getOrigin());
        }
    }
}
