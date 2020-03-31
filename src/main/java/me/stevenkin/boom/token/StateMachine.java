package me.stevenkin.boom.token;

import static me.stevenkin.boom.kit.CharKit.*;

public class StateMachine {
    private State state;
    private StringBuilder buffer;
    private boolean isDecimal;
    private String line;
    private int i;
    private LineNumReader reader;
    private RoleList roles;

    public StateMachine(LineNumReader reader) {
        initRoles();
        this.reader = reader;
        this.state = State.SEPARATE;
        this.buffer = new StringBuilder();
        this.isDecimal = false;
        this.i = 0;
    }

    public Token next() {
        for (;;) {
            if (line == null || i >= line.length()) {
                line = reader.readLine();
                if (line == null)
                    return Token.END;
                line = line + "\n";
                i = 0;
            }
            Role role1 = null;
            for (Role role : roles) {
                if (role.match()) {
                    role1 = role;
                    break;
                }
            }
            if (role1 == null)
                throw new RuntimeException();
            Token token = role1.action();
            if (token != null)
                return token;
        }
    }

    private void initRoles() {
        roles = new RoleList();
        roles.addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isBlank(line.charAt(i));
            }

            @Override
            public Token action() {
                i++;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isOp(line.charAt(i));
            }

            @Override
            public Token action() {
                Token token;
                if (!isSingleOp(line.charAt(i)) && i + 1 < line.length() && isOp(line.charAt(i + 1))) {
                    token = new Token(new StringBuilder().append(line.charAt(i)).append(line.charAt(i + 1)).toString(), reader.getCurrLineNum(), Token.Type.OP);
                    i += 2;
                    if (token.getOrigin().equals("//")) {
                        state = State.ANNOTATE;
                        return null;
                    }
                } else {
                    token = new Token(line.charAt(i) + "", reader.getCurrLineNum(), Token.Type.OP);
                    i++;
                    return token;
                }
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isBrackets(line.charAt(i));
            }

            @Override
            public Token action() {
                return new Token(line.charAt(i++) + "", reader.getCurrLineNum(), Token.Type.BRACKETS);
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && (isPoint(line.charAt(i)) || isNum(line.charAt(i)));
            }

            @Override
            public Token action() {
                if (isPoint(line.charAt(i)))
                    isDecimal = true;
                state = State.NUMBER;
                buffer.append(line.charAt(i++));
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && (isSemicolon(line.charAt(i)) || isComma(line.charAt(i)) || isPoint(line.charAt(i)));
            }

            @Override
            public Token action() {
                Token.Type type;
                if (isSemicolon(line.charAt(i)))
                    type = Token.Type.SEMICOLON;
                else if (isComma(line.charAt(i)))
                    type = Token.Type.COMMA;
                else
                    type = Token.Type.POINT;
                Token token = new Token(line.charAt(i) + "", reader.getCurrLineNum(), type);
                i++;
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.NUMBER && (isPoint(line.charAt(i)) || isNum(line.charAt(i)));
            }

            @Override
            public Token action() {
                if (isDecimal && isPoint(line.charAt(i)))
                    throw new RuntimeException();
                if (isPoint(line.charAt(i)))
                    isDecimal = true;
                buffer.append(line.charAt(i++));
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.NUMBER && isSeparator(line.charAt(i));
            }

            @Override
            public Token action() {
                state = State.SEPARATE;
                Token token = new Token(buffer.toString(), reader.getCurrLineNum(), Token.Type.NUMBER);
                buffer = new StringBuilder();
                isDecimal = false;
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.NUMBER && !(isPoint(line.charAt(i)) || isNum(line.charAt(i))) && !isSeparator(line.charAt(i));
            }

            @Override
            public Token action() {
                throw new RuntimeException();
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isDqm(line.charAt(i));
            }

            @Override
            public Token action() {
                state = State.STRING;
                i++;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.STRING && !isDqm(line.charAt(i)) && !isEscape(line.charAt(i));
            }

            @Override
            public Token action() {
                buffer.append(line.charAt(i++));
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.STRING && isEscape(line.charAt(i));
            }

            @Override
            public Token action() {
                if (i + 1 >= line.length())
                    throw new RuntimeException();
                if (line.charAt(i + 1) == 'u') {
                    i++;
                    int end = i + 4;
                    if (end >= line.length())
                        throw new RuntimeException();
                    StringBuilder sb = new StringBuilder();
                    for (i = i + 1; i < end + 1; i++) {
                        sb.append(line.charAt(i));
                    }
                    buffer.append(optUnicode(sb.toString()));
                    return null;
                }
                buffer.append(escape(line.charAt(++i)));
                i++;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.STRING && isDqm(line.charAt(i));
            }

            @Override
            public Token action() {
                Token token = new Token(buffer.toString(), reader.getCurrLineNum(), Token.Type.STRING);
                buffer = new StringBuilder();
                state = State.SEPARATE;
                i++;
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isSqm(line.charAt(i));
            }

            @Override
            public Token action() {
                i++;
                state = State.CHAR;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.CHAR;
            }

            @Override
            public Token action() {
                Token token;
                if (isEscape(line.charAt(i))) {
                    if (i + 2 >= line.length())
                        throw new RuntimeException();
                    if (!isSqm(line.charAt(i + 2)))
                        throw new RuntimeException();
                    token = new Token(escape(line.charAt(i + 1)) + "", reader.getCurrLineNum(), Token.Type.CHAR);
                    i += 3;
                } else {
                    if (i + 1 >= line.length())
                        throw new RuntimeException();
                    if (!isSqm(line.charAt(i + 1)))
                        throw new RuntimeException();
                    token = new Token(line.charAt(i) + "", reader.getCurrLineNum(), Token.Type.CHAR);
                    i += 2;
                }
                state = State.SEPARATE;
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.SEPARATE && isIdentifier(line.charAt(i));
            }

            @Override
            public Token action() {
                state = State.IDENTIFIER;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.IDENTIFIER && (isIdentifier(line.charAt(i)) || isNum(line.charAt(i)));
            }

            @Override
            public Token action() {
                buffer.append(line.charAt(i));
                i++;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.IDENTIFIER && (isSeparator(line.charAt(i)) || isPoint(line.charAt(i)));
            }

            @Override
            public Token action() {
                Token token = new Token(buffer.toString(), reader.getCurrLineNum(), Token.Type.IDENTIFIER);
                buffer = new StringBuilder();
                state = State.SEPARATE;
                return token;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.ANNOTATE && line.charAt(i) != '\n';
            }

            @Override
            public Token action() {
                i++;
                return null;
            }
        }).addRole(new Role() {
            @Override
            public boolean match() {
                return state == State.ANNOTATE && line.charAt(i) == '\n';
            }

            @Override
            public Token action() {
                i++;
                state = State.SEPARATE;
                return null;
            }
        });
    }
}
