package me.stevenkin.boom.token;

import java.io.BufferedReader;
import java.io.IOException;

public class LineNumReader {
    private BufferedReader reader;
    private int currLineNum;

    public LineNumReader(BufferedReader reader) {
        this.reader = reader;
        this.currLineNum = 0;
    }

    public String readLine() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        if (line != null)
            currLineNum++;
        return line;
    }

    public int getCurrLineNum() {
        return currLineNum;
    }
}
