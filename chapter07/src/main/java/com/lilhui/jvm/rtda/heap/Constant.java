package com.lilhui.jvm.rtda.heap;

public interface Constant {

    public <T> void setValue(T value);

    public <T> T getValue();
}
