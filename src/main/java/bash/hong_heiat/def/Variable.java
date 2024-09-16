package bash.hong_heiat.def;

public class Variable extends Value {
    private String name;

    public Variable(String name, Type type, Object value) {
        super(value, type);
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return handle;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "type=" + type +
                ", value=" + handle +
                ", name='" + name + '\'' +
                '}';
    }
}
