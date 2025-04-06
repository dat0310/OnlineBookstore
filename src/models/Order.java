package models;

public class Order {
    private String orderId;
    private String customerName;
    private Book[] books;
    private String status;

    public Order(String orderId, String customerName, Book[] books) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.books = books;
        this.status = "Pending";
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public Book[] getBooks() { return books; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order ID: %s, Customer: %s, Status: %s\n",
                orderId, customerName, status));
        sb.append("Books in order:\n");
        for (Book book : books) {
            sb.append("  ").append(book.toString()).append("\n");
        }
        return sb.toString();
    }
}