package bash.hong_heiat.def;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Type {
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                '}';
    }
}
