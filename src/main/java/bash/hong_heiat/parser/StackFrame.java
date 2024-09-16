package bash.hong_heiat.parser;

import bash.hong_heiat.def.Type;
import bash.hong_heiat.def.Value;
import bash.hong_heiat.def.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StackFrame {
    private Map<String, Variable> variables = new HashMap<>();
    private Map<String, Type> types = new HashMap<>();
    private StackFrame parent;

    public StackFrame(StackFrame parent) {
        this.parent = parent;
    }

    public void setTypes(Map<String, Type> types) {
        this.types = types;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Optional<Variable> getVariable(String name) {
        Variable variable = variables.get(name);
        if (variable != null) return Optional.of(variable);
        if (parent != null) {
            var maybeVariable = parent.getVariable(name);
            if (maybeVariable.isPresent()) return Optional.of(variable);
        }
        return Optional.empty();
    }

    public Variable createVariable(Variable variable) throws Exception {
        if (variables.containsKey(variable.getName())) throw new Exception("Variable " + variable.getName() + " already has been defined in this scope!");
        variables.put(variable.getName(), variable);
        return variable;
    }

    public void setVariables(Map<String, Variable> variables) {
        this.variables = variables;
    }

    public Optional<Type> getType(String name) {
        Type type = types.get(name);
        if (type != null) return Optional.of(type);
        if (parent != null) {
            var maybeType = parent.getType(name);
            if (maybeType.isPresent()) return Optional.of(type);
        }
        return Optional.empty();
    }

    public Type createType(String name) throws Exception {
        if (types.containsKey(name)) throw new Exception("Type " + name + " already has been defined in this scope!");
        Type type = new Type(name);
        types.put(name, type);
        return type;
    }
}
