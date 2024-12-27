package LMS.app.src.main.java.org.example;

import java.util.ArrayList;
import java.util.List;

class Book {
    private String title, author;
    private boolean isAvailable = true;

    public Book(String title, String author) {
        this.title = title; this.author = author;
    }

    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }

    public void borrowBook() { 
        if (!isAvailable) throw new IllegalStateException("Book is already borrowed.");
        isAvailable = false;
    }

    public void returnBook() { isAvailable = true; }
}

class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) if (book.isAvailable()) availableBooks.add(book);
        return availableBooks;
    }

    public Book findBookByTitle(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
}

class Patron {
    private String name;
    private List<Book> borrowedBooks = new ArrayList<>();

    public Patron(String name) { this.name = name; }

    public void borrowBook(Library library, String bookTitle) {
        Book book = library.findBookByTitle(bookTitle);
        if (book == null || !book.isAvailable()) throw new IllegalArgumentException("Book is not available.");
        book.borrowBook(); borrowedBooks.add(book);
    }

    public void returnBook(Library library, String bookTitle) {
        borrowedBooks.removeIf(book -> {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                book.returnBook(); return true;
            } return false;
        });
    }
}
public class App {
    public static void main(String[] args) {
        Library library = new Library();
        Patron patron = new Patron("John Doe");

        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));

        library.getAvailableBooks().forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor()));

        patron.borrowBook(library, "1984");
        System.out.println("\nJohn Doe borrowed '1984'.");

        library.getAvailableBooks().forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor()));

        patron.returnBook(library, "1984");
        System.out.println("\nJohn Doe returned '1984'.");

        library.getAvailableBooks().forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor()));
    }
}
