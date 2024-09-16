package bash.hong_heiat.parser;

import bash.hong_heiat.def.DefinitionsContainer;
import bash.hong_heiat.def.Type;
import bash.hong_heiat.def.Value;
import bash.hong_heiat.def.Variable;
import bash.hong_heiat.def.ops.BinaryOperation;
import bash.hong_heiat.lexer.Token;
import bash.hong_heiat.lexer.TokenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Parser {
    private StackFrame master;

    public void execute(List<Token> tokens) throws Exception {
        tokens = sort(tokens);
        master = new StackFrame(null);
        for (var entry : DefinitionsContainer.getTypes().entrySet()) master.createType(entry.getKey());
        int i = 0;
        while (i < tokens.size()) {
            if (tokens.get(i).getType() == TokenType.SEMICOLON) {
                i++;
                continue;
            }
            int begin = i;
            while (i < tokens.size() && tokens.get(i).getType() != TokenType.SEMICOLON) i++;
            for (int j = begin; j < i; j++) {
                if (tokens.get(j).getType() == TokenType.TYPE) {
                    Optional<Type> maybeType = master.getType(tokens.get(j).getToken());
                    if (maybeType.isEmpty()) throw new Exception("There is no type with name " + tokens.get(j).getToken());
                    if (j == begin + 1) throw new Exception("Incorrect variable definition, required: TYPE_NAME VARIABLE_NAME");
                    Value value = null;
                    if (tokens.get(begin + 2).getType() != TokenType.SEMICOLON) value = evaluate(tokens, begin + 3);
                    else throw new Exception("Too little arguments!");
                    master.createVariable(new Variable(tokens.get(begin + 1).getToken(), value.getType(), value.getHandle()));
//                    master.createVariable(new Variable(tokens.get(begin + 1).getToken(), value.getType(), new Variable(tokens.get(begin + 1).getToken(), value.getType(), value)));
                } else if (tokens.get(j).getType() == TokenType.VARIABLE) {
//                    Value value = null;
//                    if (tokens.get(begin + 2).getType() != TokenType.SEMICOLON) value = evaluate(tokens, begin + 3);
//                    else throw new Exception("Too little arguments!");
//                    DefinitionsContainer.addVariable(tokens.get(begin + 1).getToken(), new Variable(tokens.get(begin + 1).getToken(), value.getType(), value));
                }
            }
        }
    }

    public StackFrame getMaster() {
        return master;
    }

    private Value evaluate(List<Token> tokens, int beginIndex) throws Exception {
        LinkedList<Value> stack = new LinkedList<>();
        for (int i = beginIndex; i < tokens.size(); i++) {
            if (tokens.get(i).getType() == TokenType.SEMICOLON) break;
            else if (tokens.get(i).getType() == TokenType.INT_LITERAL) stack.addLast(new Value(Integer.parseInt(tokens.get(i).getToken()), DefinitionsContainer.getType("int").get()));
            else if (tokens.get(i).getType() == TokenType.VARIABLE) {
                Optional<Variable> variable = master.getVariable(tokens.get(i).getToken());
                if (variable.isEmpty()) throw new Exception("There is no variable with name " + tokens.get(i).getToken());
                stack.addLast(variable.get());
            }
            else { // Operation handling
                if (tokens.get(i).getType() == TokenType.PLUS) {
                    BinaryOperation op = DefinitionsContainer.getBinaryOp("+");
                    if (stack.size() < 2) throw new Exception("Too little arguments for + operation!");
                    Value second = stack.pollLast(), first = stack.pollLast();
                    stack.addLast(op.perform(first, second));
                } else if (tokens.get(i).getType() == TokenType.MINUS) {
                    BinaryOperation op = DefinitionsContainer.getBinaryOp("-");
                    if (stack.size() < 2) throw new Exception("Too little arguments for + operation!");
                    Value second = stack.pollLast(), first = stack.pollLast();
                    stack.addLast(op.perform(first, second));
                } else if (tokens.get(i).getType() == TokenType.MULTIPLICATION) {
                    BinaryOperation op = DefinitionsContainer.getBinaryOp("*");
                    if (stack.size() < 2) throw new Exception("Too little arguments for + operation!");
                    Value second = stack.pollLast(), first = stack.pollLast();
                    stack.addLast(op.perform(first, second));
                } else if (tokens.get(i).getType() == TokenType.DIVISION) {
                    BinaryOperation op = DefinitionsContainer.getBinaryOp("/");
                    if (stack.size() < 2) throw new Exception("Too little arguments for + operation!");
                    Value second = stack.pollLast(), first = stack.pollLast();
                    stack.addLast(op.perform(first, second));
                }
            }
        }
        if (stack.size() == 1) return stack.pollLast();
        else throw new Exception("Too little arguments!");
    }

    public List<Token> sort(List<Token> tokens) {
        int i = 0;
        List<Token> sortedTokens = new ArrayList<>();
        while (i < tokens.size()) {
            if (tokens.get(i).getPriority() == -1) {
                sortedTokens.add(tokens.get(i));
                i++;
                continue;
            }
            List<Token> subseq = new ArrayList<>();
            while (i < tokens.size() && tokens.get(i).getPriority() != -1) subseq.add(tokens.get(i++));
            LinkedList<Token> ops = new LinkedList<>();
            for (int j = 0; j < subseq.size(); j++) {
                if (subseq.get(j).getType() == TokenType.LEFT_PARENTHESIS) ops.add(subseq.get(j));
                else if (subseq.get(j).getType() == TokenType.RIGHT_PARENTHESIS) {
                    while (ops.size() > 0 && ops.getLast().getType() != TokenType.LEFT_PARENTHESIS) sortedTokens.add(ops.pollLast());
                    ops.pollLast();
                }
                else if (subseq.get(j).getPriority() == 0) sortedTokens.add(subseq.get(j));
                else if (ops.size() > 0 && ops.getLast().getPriority() <= subseq.get(j).getPriority()) ops.addLast(subseq.get(j));
                else {
                    while (ops.size() > 0 && ops.getLast().getPriority() > subseq.get(j).getPriority()) sortedTokens.add(ops.pollLast());
                    ops.addLast(subseq.get(j));
                }
            }
            while (ops.size() > 0) sortedTokens.add(ops.pollLast());
        }
        return sortedTokens;
    }
}
