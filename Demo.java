import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Book class
class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn;
    }
}

// LibraryCatalog class
class LibraryCatalog {
    private List<Book> books;

    public LibraryCatalog() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}

// Main class
public class LibrarySystem {
    private static LibraryCatalog catalog = new LibraryCatalog();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nLibrary System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Books");
            System.out.println("4. Find Book by ISBN");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    catalog.listBooks();
                    break;
                case 4:
                    findBookByIsbn();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    saveCatalogToFile("catalog.txt");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book(title, author, isbn);
        catalog.addBook(book);
        System.out.println("Book added.");
    }

    private static void removeBook() {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        catalog.removeBook(isbn);
        System.out.println("Book removed (if it existed).");
    }

    private static void findBookByIsbn() {
        System.out.print("Enter ISBN to find: ");
        String isbn = scanner.nextLine();
        Book book = catalog.findBookByIsbn(isbn);
        if (book != null) {
            System.out.println("Book found: " + book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void saveCatalogToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : catalog.books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getIsbn());
                writer.newLine();
            }
            System.out.println("Catalog saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving catalog: " + e.getMessage());
        }
    }

    private static void loadCatalogFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Book book = new Book(parts[0], parts[1], parts[2]);
                    catalog.addBook(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading catalog: " + e.getMessage());
        }
    }
}
