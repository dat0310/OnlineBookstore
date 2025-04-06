package datastructures;

import models.Order;

public class OrderQueue {
    private Order[] elements;
    private int front;
    private int rear;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public OrderQueue() {
        elements = new Order[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(Order order) {
        if (size == elements.length) {
            resize();
        }
        rear = (rear + 1) % elements.length;
        elements[rear] = order;
        size++;
    }

    public Order dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        Order order = elements[front];
        front = (front + 1) % elements.length;
        size--;
        return order;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Order[] newElements = new Order[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % elements.length];
        }
        elements = newElements;
        front = 0;
        rear = size - 1;
    }
}