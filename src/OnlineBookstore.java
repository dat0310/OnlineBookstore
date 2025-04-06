import models.Book;
import models.Order;
import datastructures.BookArrayList;
import datastructures.BookLinkedList;
import datastructures.OrderQueue;
import datastructures.OrderStack;
import algorithms.SortingAlgorithms;
import algorithms.SearchingAlgorithms;
import java.util.Scanner;

public class OnlineBookstore {
    private static int orderCounter = 1;
    private BookArrayList inventory;
    private OrderQueue unprocessedOrders;
    private OrderStack processedOrders;
    private Scanner scanner;

    public OnlineBookstore() {
        inventory = new BookArrayList();
        unprocessedOrders = new OrderQueue();
        processedOrders = new OrderStack();
        scanner = new Scanner(System.in);
        initializeSampleData();
    }

    private void initializeSampleData() {
        inventory.add(new Book("01", "1984", "George Orwell", 8.99, 20));
        inventory.add(new Book("02", "The Da Vinci Code", "Dan Brown", 9.99, 8));
        inventory.add(new Book("03", "The Great Gatsby", "F. Scott Fitzgerald", 12.50, 10));
        inventory.add(new Book("04", "The Lord of the Rings", "J.R.R. Tolkien", 15.99, 12));
        inventory.add(new Book("05", "To Kill a Mockingbird", "Harper Lee", 10.99, 15));
    }

    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: displayInventory(); break;
                case 2: placeOrder(); break;
                case 3: processOrders(); break;
                case 4: trackOrder(); break;
                case 5: viewAllOrders(); break;
                case 6: running = false;
                    System.out.println("Exiting Online Bookstore. Thank you!"); break;
                default: System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== Online Bookstore System ===");
        System.out.println("1. Display Inventory");
        System.out.println("2. Place New Order");
        System.out.println("3. Process Pending Orders");
        System.out.println("4. Track Order Status");
        System.out.println("5. View All Orders");
        System.out.println("6. Exit System");
        System.out.print("Enter your choice (1-6): ");
    }

    private void displayInventory() {
        Book[] books = inventory.toArray();
        SortingAlgorithms.mergeSort(books);
        showAllBooks(books);

        boolean searching = true;
        while (searching) {
            System.out.println("\n=== Search Options ===");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.print("Choose search option (0-3): ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    searching = false;
                    break;
                case 1:
                case 2:
                case 3:
                    searchBooks(books, choice);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void searchBooks(Book[] books, int searchType) {
        System.out.print("Enter search term (or '0' to cancel): ");
        String searchTerm = scanner.nextLine();

        if (searchTerm.equals("0")) {
            return;
        }

        System.out.println("\n=== Search Results ===");
        boolean found = false;

        SearchingAlgorithms.SearchType type;
        switch (searchType) {
            case 1: type = SearchingAlgorithms.SearchType.ID; break;
            case 2: type = SearchingAlgorithms.SearchType.TITLE; break;
            case 3: type = SearchingAlgorithms.SearchType.AUTHOR; break;
            default: type = SearchingAlgorithms.SearchType.TITLE;
        }

        for (Book book : books) {
            Book result = SearchingAlgorithms.linearSearch(new Book[]{book}, searchTerm, type);
            if (result != null) {
                System.out.println(formatBookDetails(book));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found matching your search.");
        }
    }

    private void showAllBooks(Book[] books) {
        System.out.println("\n=== All Books ===");
        for (Book book : books) {
            System.out.println(formatBookDetails(book));
        }
    }

    private String formatBookDetails(Book book) {
        return String.format("ID: %s | Title: %-25s | Author: %-20s | Price: $%-6.2f | Stock: %d",
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getQuantity());
    }

    private void placeOrder() {
        System.out.println("\n=== Place New Order ===");
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        Book[] books = inventory.toArray();
        SortingAlgorithms.mergeSort(books);
        showAllBooks(books);

        BookLinkedList orderBooks = new BookLinkedList();
        boolean addingBooks = true;

        while (addingBooks) {
            System.out.print("\nEnter book ID to add to order (or '0' to finish): ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                addingBooks = false;
            } else {
                Book book = findBookById(input);

                if (book != null) {
                    if (book.getQuantity() == 0) {
                        System.out.println("Sorry, '" + book.getTitle() + "' is currently out of stock!");
                        continue;
                    }

                    int quantity = -1;
                    while (quantity < 1) {
                        System.out.print("Enter quantity (minimum 1, available: " + book.getQuantity() + "): ");
                        try {
                            quantity = scanner.nextInt();
                            if (quantity < 1) {
                                System.out.println("Quantity must be at least 1!");
                            } else if (quantity > book.getQuantity()) {
                                System.out.println("Not enough stock! Available: " + book.getQuantity());
                                quantity = -1;
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number!");
                            scanner.nextLine();
                            quantity = -1;
                        }
                    }
                    scanner.nextLine();

                    orderBooks.add(new Book(book.getId(), book.getTitle(),
                            book.getAuthor(), book.getPrice(), quantity));
                    book.setQuantity(book.getQuantity() - quantity);
                    System.out.println("Added " + quantity + " of '" + book.getTitle() + "' to order.");
                    System.out.println("Remaining stock: " + book.getQuantity());

                    System.out.println("\n=== Updated Book List ===");
                    showAllBooks(books);
                } else {
                    System.out.println("Book with ID " + input + " not found in inventory.");
                }
            }
        }

        if (orderBooks.size() > 0) {
            String orderId = String.format("ORD%04d", orderCounter++);
            Book[] booksArray = orderBooks.toArray();
            Order newOrder = new Order(orderId, customerName, booksArray);
            unprocessedOrders.enqueue(newOrder);
            System.out.println("\nOrder placed successfully!");
            System.out.println("Your Order ID: " + orderId);
        } else {
            System.out.println("Order cancelled - no items were added.");
        }
    }

    private Book findBookById(String id) {
        Book[] books = inventory.toArray();
        return SearchingAlgorithms.linearSearch(books, id, SearchingAlgorithms.SearchType.ID);
    }

    private void processOrders() {
        System.out.println("\n=== Processing Orders ===");
        if (unprocessedOrders.isEmpty()) {
            System.out.println("No orders to process!");
            return;
        }

        System.out.println("Processing " + unprocessedOrders.size() + " orders...");
        while (!unprocessedOrders.isEmpty()) {
            Order order = unprocessedOrders.dequeue();
            Book[] books = order.getBooks();
            SortingAlgorithms.insertionSort(books);
            order.setStatus("Processed");
            processedOrders.push(order);
            System.out.println("Processed order: " + order.getOrderId());
        }
        System.out.println("All orders processed successfully!");
    }

    private void trackOrder() {
        System.out.println("\n=== Track Order ===");
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        boolean found = false;

        OrderQueue tempQueue = new OrderQueue();
        while (!unprocessedOrders.isEmpty()) {
            Order order = unprocessedOrders.dequeue();
            tempQueue.enqueue(order);
            if (order.getOrderId().equals(orderId)) {
                System.out.println("\nOrder Details:");
                System.out.println(order);
                found = true;
                break;
            }
        }
        while (!tempQueue.isEmpty()) {
            unprocessedOrders.enqueue(tempQueue.dequeue());
        }

        if (!found) {
            OrderStack tempStack = new OrderStack();
            while (!processedOrders.isEmpty()) {
                Order order = processedOrders.pop();
                tempStack.push(order);
                if (order.getOrderId().equals(orderId)) {
                    System.out.println("\nOrder Details:");
                    System.out.println(order);
                    found = true;
                    break;
                }
            }
            while (!tempStack.isEmpty()) {
                processedOrders.push(tempStack.pop());
            }
        }

        if (!found) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    private void viewAllOrders() {
        System.out.println("\n=== All Orders ===");

        System.out.println("\n=== Processed Orders ===");
        OrderStack tempProcessed = new OrderStack();
        int processedCount = 0;

        while (!processedOrders.isEmpty()) {
            Order order = processedOrders.pop();
            tempProcessed.push(order);
            System.out.println(order);
            System.out.println("-----------------------");
            processedCount++;
        }
        while (!tempProcessed.isEmpty()) {
            processedOrders.push(tempProcessed.pop());
        }

        if (processedCount == 0) {
            System.out.println("No processed orders found.");
        }

        System.out.println("\n=== Unprocessed Orders ===");
        OrderQueue tempUnprocessed = new OrderQueue();
        int unprocessedCount = 0;

        while (!unprocessedOrders.isEmpty()) {
            Order order = unprocessedOrders.dequeue();
            tempUnprocessed.enqueue(order);
            System.out.println(order);
            System.out.println("-----------------------");
            unprocessedCount++;
        }
        while (!tempUnprocessed.isEmpty()) {
            unprocessedOrders.enqueue(tempUnprocessed.dequeue());
        }

        if (unprocessedCount == 0) {
            System.out.println("No unprocessed orders found.");
        }

        System.out.println("\nTotal: " + (processedCount + unprocessedCount) + " orders");
    }

    public static void main(String[] args) {
        new OnlineBookstore().run();
    }
}