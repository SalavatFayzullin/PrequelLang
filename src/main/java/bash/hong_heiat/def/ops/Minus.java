package bash.hong_heiat.def.ops;

import bash.hong_heiat.def.Value;

public class Minus implements BinaryOperation {
    @Override
    public Value perform(Value a, Value b) {
        if (a.getHandle() instanceof Integer i1 && b.getHandle() instanceof Integer i2) return new Value(i1 - i2, a.getType());
        return null;
    }
}
