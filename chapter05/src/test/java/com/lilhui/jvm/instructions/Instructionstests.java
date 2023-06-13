package com.lilhui.jvm.instructions;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/12 11:18
 */
public class Instructionstests {

    @Test
    public void testCast() {
        double f = 3.14;
        long val = Double.doubleToRawLongBits(f);
        assertEquals(4622945017495814144L, val);
    }

    @Test
    public void testCast2() {
        int a = 10;
        float f = (float) a;
        assertEquals(10.0f, f, 0.001f);
    }

    @Test
    public void testFmt() {
        byte[] bytes = new byte[]{1, 2, 3};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        assertEquals(ByteBuffer.class, buffer.getClass());
    }
}
