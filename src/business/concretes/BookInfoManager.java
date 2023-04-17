package business.concretes;

import business.abstracts.MenuService;
import core.enums.BookStatus;
import core.enums.BookTypes;
import core.utilities.InputUtilities;
import dataaccess.concretes.BookDAO;
import entities.concretes.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookInfoManager implements MenuService {

    private BookDAO bookDAO;

    private List<Book> bookList;

    private Scanner inp;


    public BookInfoManager(BookDAO bookDAO, Scanner scanner) {
        this.bookDAO = bookDAO;
        bookList = bookDAO.getData();
        inp = scanner;
    }

    @Override
    public void showProcessMenu() {

        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 1 > ADD BOOK" + " ".repeat(15) + "|" + "\t\t" + "|\t< 2 > UPDATE BOOK INFO" + " ".repeat(7) + "|");
            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 3 > FIND BOOK" + " ".repeat(14) + "|" + "\t\t" + "|\t< 4 > LIST ALL BOOKS" + " ".repeat(9) + "|");
            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");
            System.out.println("\t\t|" + " ".repeat(27) + "< 0 > START MENU" + " ".repeat(29) + "|");
            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");


            System.out.print("\t\tSELECT : ");
            choice = InputUtilities.scanInt();

            switch (choice) {
                case 0:
                    System.out.println("\n\t\tRedirected to the Start Menu\n");
                    break;
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBookInfo(takeBookId());
                    break;
                case 3:
                    findBook(takeBookId());
                    break;
                case 4:
                    listAllBooks();
                    break;
                default:
                    System.out.println("\t\tInvalid Selection");
                    break;
            }
        }
    }


    private void addBook() {
        Book book = createBook();
        boolean added = bookList.add(book);

        if (added) {
            System.out.println("\t\tNew book successfully added");
        } else {
            System.out.println("\t\tThis book cannot be added");
        }

    }

    private void updateBookInfo(String bookId) {

        int updateSelection = -1;
        do {
            System.out.println("\t\t+" + "-".repeat(72) + "+");
            System.out.println("\t\t|   < 1 > UPDATE JUST STATUS         |   < 2 > UPDATE ALL INFORMATION    |");
            System.out.println("\t\t+" + "-".repeat(72) + "+");
            System.out.print("\t\tSELECT : ");
            updateSelection = InputUtilities.scanInt();
        } while (updateSelection != 1 && updateSelection != 2);

        if (updateSelection == 1) {

            if (bookList.contains(new Book(bookId))) {
                Book found = bookList.get(bookList.indexOf(new Book(bookId)));

                BookStatus.toList();
                System.out.printf("\t\t%-14s : ", "SELECT STATUS");
                BookStatus bookStatus = BookStatus.of(InputUtilities.scanInt());

                found.setBookStatus(bookStatus);
                System.out.println("\t\tBook status updated");
            } else {
                System.out.println("\t\tBook cannot found");
            }

        } else {

            if (bookList.contains(new Book(bookId))) {
                Book found = bookList.get(bookList.indexOf(new Book(bookId)));
                Book book = createBook();
                found.setBookName(book.getBookName());
                found.setAuthorName(book.getAuthorName());
                found.setPublishYear(book.getPublishYear());
                found.setPublisher(book.getPublisher());
                found.setBookType(book.getBookType());
                found.setBookStatus(book.getBookStatus());
                System.out.println("\t\tBook information updated");
            } else {
                System.out.println("\t\tBook cannot found");
            }

        }


    }

    private void findBook(String bookId) {

        if (bookList.contains(new Book(bookId))) {
            Book found = bookList.get(bookList.indexOf(new Book(bookId)));

            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
            System.out.printf("\t\t|\t%-13s | %-25s | %-20s | %-20s | %-16s | %-18s | %-10s |\n", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "PUBLISHER", "PUBLICATION YEAR", "TYPE", "STATUS");
            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
            System.out.printf("\t\t| %-15s | %-25s | %-20s | %-20s | %-16s | %-18s | %-10s |\n",
                    found.getBookId(), found.getBookName(), found.getAuthorName(), found.getPublisher(), found.getPublishYear(), found.getBookType(), found.getBookStatus());
            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");

        } else {
            System.out.println("\t\tBook cannot found");
        }


    }

    private void listAllBooks() {
        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
        System.out.printf("\t\t|\t%-13s | %-25s | %-20s | %-20s | %-16s | %-18s | %-10s |\n", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "PUBLISHER", "PUBLICATION YEAR", "TYPE", "STATUS");
        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
        for (Book book : this.bookList) {
            System.out.printf("\t\t| %-15s | %-25s | %-20s | %-20s | %-16s | %-18s | %-10s |\n",
                    book.getBookId(), book.getBookName(), book.getAuthorName(), book.getPublisher(), book.getPublishYear(), book.getBookType(), book.getBookStatus());
        }
        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
    }

    private String takeBookId() {
        System.out.printf("\t\t%-14s : ", "Book ID");
        return inp.nextLine().toUpperCase().trim();
    }

    private Book createBook() {
        System.out.printf("\t\t%-14s : ", "Book Name");
        String name = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Author Name");
        String authorName = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Publisher");
        String publisher = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Publish Year");
        int publicationYear = InputUtilities.scanInt();

        BookTypes.toList();
        System.out.printf("\t\t%-14s : ", "SELECT TYPE");
        BookTypes bookType = BookTypes.of(InputUtilities.scanInt());


        Book book = new Book(name, authorName, publisher, publicationYear, bookType, BookStatus.of(1));
        return book;
    }

}
