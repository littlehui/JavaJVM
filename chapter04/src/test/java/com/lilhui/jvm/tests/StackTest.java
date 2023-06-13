package com.lilhui.jvm.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:25
 */
public class StackTest {

    @Test
    public void testStack() {
        // Implement your test code here
        // ...
        // fail("Test not implemented");
    }

    @Test
    public void testMath() {
        int bits = Float.floatToIntBits(3.14f);
        float f32 = Float.intBitsToFloat(bits);
        assertEquals(3.14f, f32, 0.001f);
    }
}
