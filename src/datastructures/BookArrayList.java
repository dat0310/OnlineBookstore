package datastructures;

import models.Book;

public class BookArrayList {
    private Book[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public BookArrayList() {
        elements = new Book[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(Book book) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = book;
    }

    private void resize() {
        Book[] newElements = new Book[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    public Book[] toArray() {
        Book[] array = new Book[size];
        System.arraycopy(elements, 0, array, 0, size);
        return array;
    }
}