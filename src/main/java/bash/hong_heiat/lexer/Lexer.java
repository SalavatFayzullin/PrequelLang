package bash.hong_heiat.lexer;

import bash.hong_heiat.def.DefinitionsContainer;
import bash.hong_heiat.def.Type;
import bash.hong_heiat.def.Variable;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String src;
    private int ptr = 0;
    private List<Token> tokens;
    public List<Token> tokenize(String sourceCode) {
        src = sourceCode;
        ptr = 0;
        tokens = new ArrayList<>();
        while (hasNext()) tokens.add(next());
        return tokens;
    }
    public boolean hasNext() {
        while (ptr < src.length() && Character.isSpaceChar(src.charAt(ptr))) ptr++;
        if (ptr == src.length()) return false;
        return true;
    }
    public Token next() {
        if (src.charAt(ptr) == '"') {

        } else if (src.charAt(ptr) == '+') {
            ptr++;
            return new Token(TokenType.PLUS, 1, "+");
        } else if (src.charAt(ptr) == ';') {
            ptr++;
            return new Token(TokenType.SEMICOLON, -1, ";");
        } else if (src.charAt(ptr) == '=') {
            ptr++;
            return new Token(TokenType.ASSIGN, -1, "=");
        } else if (Character.isDigit(src.charAt(ptr))) {
            int previous = ptr;
            while (ptr < src.length() && Character.isDigit(src.charAt(ptr))) ptr++;
            String stringToken = src.substring(previous, ptr);
            return new Token(TokenType.INT_LITERAL,0, stringToken);
        } else if (src.charAt(ptr) == '-') {
            ptr++;
            return new Token(TokenType.MINUS, 1, "-");
        } else if (src.charAt(ptr) == '*') {
            ptr++;
            return new Token(TokenType.MULTIPLICATION, 2, "*");
        } else if (src.charAt(ptr) == '/') {
            ptr++;
            return new Token(TokenType.DIVISION, 2, "/");
        } else if (src.charAt(ptr) == '(') {
            ptr++;
            return new Token(TokenType.LEFT_PARENTHESIS, 0, "(");
        } else if (src.charAt(ptr) == ')') {
            ptr++;
            return new Token(TokenType.RIGHT_PARENTHESIS, 0, ")");
        }
        else {
            int previous = ptr;
            while (ptr < src.length() && (Character.isDigit(src.charAt(ptr))) || Character.isAlphabetic(src.charAt(ptr))) ptr++;
            String stringToken = src.substring(previous, ptr);
            var maybeType = DefinitionsContainer.getType(stringToken);
            if (maybeType.isPresent()) return new Token(TokenType.TYPE, -1, stringToken);
            var maybeVariable = DefinitionsContainer.getVariable(stringToken);
            if (maybeVariable.isPresent()) return new Token(TokenType.VARIABLE, 0, stringToken);
            if (tokens.size() > 0) return new Token(TokenType.VARIABLE, 0, stringToken);
            return null;
        }
        return null;
    }
}
