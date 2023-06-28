package com.lilhui.jvm.typedef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:29
 */
public class TypeDef {
    public static class U1 extends Number {

        private final short value;

        public U1(short value) {
            this.value = (short) (value & 0xFF);
        }

        public U1(int value) {
            this((short) value);
        }

        public U1(long value) {
            this((short) value);
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class U2 extends Number {

        private final int value;

        public U2(int value) {
            this.value = value & 0xFFFF;
        }

        public U2(long value) {
            this((int) value);
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class U4 extends Number {

        private final long value;

        public U4(long value) {
            this.value = value & 0xFFFFFFFFL;
        }

        @Override
        public int intValue() {
            return (int) value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class U8 extends Number {

        private final long value;

        public U8(long value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class I1 extends Number {

        private final byte value;

        public I1(byte value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class I2 extends Number {

        private final short value;

        public I2(short value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class I4 extends Number {

        private final int value;

        public I4(int value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class I8 extends Number {

        private final long value;

        public I8(long value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return (int) value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class F4 extends Number {

        private final float value;

        public F4(float value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return (int) value;
        }

        @Override
        public long longValue() {
            return (long) value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }

    public static class F8 extends Number {

        private final double value;

        public F8(double value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return (int) value;
        }

        @Override
        public long longValue() {
            return (long) value;
        }

        @Override
        public float floatValue() {
            return (float) value;
        }

        @Override
        public double doubleValue() {
            return value;
        }
    }
}

