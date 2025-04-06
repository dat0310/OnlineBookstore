package datastructures;

import models.Order;

public class OrderStack {
    private Order[] elements;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;

    public OrderStack() {
        elements = new Order[DEFAULT_CAPACITY];
        top = -1;
    }

    public void push(Order order) {
        if (top == elements.length - 1) {
            resize();
        }
        elements[++top] = order;
    }

    public Order pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    private void resize() {
        Order[] newElements = new Order[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }
}