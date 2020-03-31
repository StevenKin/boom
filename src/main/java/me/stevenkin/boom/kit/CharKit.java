package me.stevenkin.boom.kit;

public class CharKit {
    public static boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }
    public static boolean isBraceL(char c) {
        return c == '{';
    }

    public static boolean isBraceR(char c) {
        return c == '}';
    }

    public static boolean isCurvesL(char c) {
        return c == '(';
    }

    public static boolean isCurvesR(char c) {
        return c == ')';
    }

    public static boolean isSquareL(char c) {
        return c == '[';
    }

    public static boolean isSquareR(char c) {
        return c == ']';
    }

    /*public static boolean isOperator(char c) {
        if (!isOp(c))
            return false;
        if (isSingleOp(c))
            return true;
        if (i + 1 < line.length() && isOp(line.charAt(i + 1))) {
            i++;
            buffer.append(line.charAt(i));
        }
        return true;
    }*/

    public static boolean isOp(char c) {
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

    public static boolean isSingleOp(char c) {
        return c == '!'
                || c == '^'
                || c == ':'
                || c == '?';
    }

    public static boolean isPoint(char c) {
        return c == '.';
    }

    public static boolean isComma(char c) {
        return c == ',';
    }

    public static boolean isSemicolon(char c) {
        return c == ';';
    }

    public static boolean isDqm(char c) {
        return c == '"';
    }

    public static boolean isSqm(char c) {
        return c == '\'';
    }

    public static boolean isBlank(char c) {
        return c == '\n'
                || c == '\t'
                || c == ' ';
    }

    public static boolean isEscape(char c) {
        return c == '\\';
    }

    public static char escape(char c) {
        char c1;
        switch(c) {
            case '\\':
                c1 ='\\';
                break;
            case '\'':
                c1 = '\'';
                break;
            case '\"':
                c1 = '\"';
                break;
            case 'b':
                c1 = '\b';
                break;
            case 't':
                c1 = '\t';
                break;
            case 'n':
                c1 = '\n';
                break;
            case 'f':
                c1 = '\f';
                break;
            case 'r':
                c1 = '\r';
                break;
            default:
                throw new RuntimeException();
        }
        return c1;
    }

    public static char optUnicode(String unicode){
        try {
            int charInt = Integer.parseInt(unicode, 16);
            return (char)charInt;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public static boolean isBrackets(char c) {
        return isBraceL(c) || isCurvesL(c) || isSquareL(c) || isBraceR(c) || isCurvesR(c) || isSquareR(c);
    }

    public static boolean isIdentifier(char c) {
        return !isBlank(c) && !isOp(c) && !isBrackets(c) && !isPoint(c) && !isNum(c) && !isDqm(c) && !isSqm(c) && !isSemicolon(c) && !isEscape(c);
    }

    public static boolean isSeparator(char c) {
        return isBlank(c) || isBrackets(c) || isOp(c) || isSemicolon(c) || isComma(c) || isSqm(c) || isDqm(c);
    }
}
