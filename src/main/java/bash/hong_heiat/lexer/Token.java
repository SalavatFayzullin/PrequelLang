package bash.hong_heiat.lexer;

public class Token {
    private final TokenType type;
    private int priority = -1;
    private String token;

    public Token(TokenType type, int priority, String token) {
        this.type = type;
        this.priority = priority;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", priority=" + priority +
                ", token='" + token + '\'' +
                '}';
    }
}
