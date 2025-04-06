package models;

public class Book {
    private String id;
    private String title;
    private String author;
    private double price;
    private int quantity;

    public Book(String id, String title, String author, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("ID: %s, Title: %s, Author: %s, Price: $%.2f, Quantity: %d",
                id, title, author, price, quantity);
    }
}