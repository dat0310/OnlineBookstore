package algorithms;

import models.Book;

public class SortingAlgorithms {
    public static void insertionSort(Book[] books) {
        for (int i = 1; i < books.length; i++) {
            Book key = books[i];
            int j = i - 1;

            while (j >= 0 && books[j].getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books[j + 1] = books[j];
                j--;
            }
            books[j + 1] = key;
        }
    }

    public static void mergeSort(Book[] books) {
        if (books.length < 2) {
            return;
        }
        int mid = books.length / 2;
        Book[] left = new Book[mid];
        Book[] right = new Book[books.length - mid];

        System.arraycopy(books, 0, left, 0, mid);
        System.arraycopy(books, mid, right, 0, books.length - mid);

        mergeSort(left);
        mergeSort(right);

        merge(books, left, right);
    }

    private static void merge(Book[] books, Book[] left, Book[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].getTitle().compareToIgnoreCase(right[j].getTitle()) <= 0) {
                books[k++] = left[i++];
            } else {
                books[k++] = right[j++];
            }
        }
        while (i < left.length) {
            books[k++] = left[i++];
        }
        while (j < right.length) {
            books[k++] = right[j++];
        }
    }
}