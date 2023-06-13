package com.lilhui.jvm.util.stack;

import com.lilhui.jvm.typedef.TypeDef.*;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:31
 */
public class Stack {
    private final U4 capacity;
    private U4 size;
    private Node top;

    private static class Node {
        private final Object element;
        private final Node next;

        public Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    public Stack(U4 capacity) {
        this.capacity = capacity;
        this.size = new U4(0);
        this.top = null;
    }

    public void push(Object element) {
        if (size.intValue() >= capacity.intValue()) {
            throw new StackOverflowError("StackOverflowError at util.stack.Stack.push(Object element)");
        }
        size = new U4(size.longValue()+1);
        Node topNode = new Node(element, top);
        top = topNode;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("stack is empty!");
        }
        size = new U4(size.intValue() - 1);
        Node topNode = top;
        top = topNode.next;
        return topNode.element;
    }

    public Object top() {
        return top.element;
    }

    public boolean isEmpty() {
        return size.intValue() <= 0;
    }

    public U4 size() {
        return size;
    }
}
