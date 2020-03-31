package me.stevenkin.boom.token;

public interface Role {
    boolean match();

    Token action();
}
