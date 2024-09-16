package bash.hong_heiat.def;

import bash.hong_heiat.def.ops.*;

import java.util.*;

public class DefinitionsContainer {
    private static final Map<String, Type> types = new HashMap<>();
    private static final Map<String, Variable> variables = new HashMap<>();
    private static final Map<String, BinaryOperation> binaryOps = new HashMap<>();
    static {
        binaryOps.put("+", new Plus());
        binaryOps.put("-", new Minus());
        binaryOps.put("*", new Multiplication());
        binaryOps.put("/", new Division());
        types.put("int", new Type("int"));
    }
    public static Optional<Type> getType(String token) {
        for (Type type : types.values()) {
            if (type.getName().equals(token)) return Optional.of(type);
        }
        return Optional.empty();
    }

    public static Optional<Variable> getVariable(String token) {
        for (Variable variable : variables.values()) {
            if (variable.getName().equals(token)) return Optional.of(variable);
        }
        return Optional.empty();
    }

    public static Map<String, Type> getTypes() {
        return types;
    }

    public static void addVariable(String name, Variable variable) {
        variables.put(name, variable);
    }

    public static BinaryOperation getBinaryOp(String code) {
        return binaryOps.get(code);
    }
}
