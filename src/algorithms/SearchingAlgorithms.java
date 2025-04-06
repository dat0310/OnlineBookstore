package algorithms;

import models.Book;

public class SearchingAlgorithms {
    public static Book linearSearch(Book[] books, String searchTerm, SearchType type) {
        for (Book book : books) {
            switch (type) {
                case ID:
                    if (book.getId().equalsIgnoreCase(searchTerm)) {
                        return book;
                    }
                    break;
                case TITLE:
                    if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                        return book;
                    }
                    break;
                case AUTHOR:
                    if (book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase())) {
                        return book;
                    }
                    break;
            }
        }
        return null;
    }

    public enum SearchType {
        ID, TITLE, AUTHOR
    }
}