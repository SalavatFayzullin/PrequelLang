package bash.hong_heiat.def;

public class Value {
    protected Object handle;
    protected Type type;

    public Value(Object handle, Type type) {
        this.handle = handle;
        this.type = type;
    }

    public Object getHandle() {
        return handle;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Value{" +
                "type=" + type +
                ", handle=" + handle +
                '}';
    }
}
