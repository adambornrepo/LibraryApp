package business.concretes;

import business.abstracts.MenuService;
import core.enums.BookStatus;
import core.utilities.InputUtilities;
import dataaccess.concretes.BookDAO;
import dataaccess.concretes.BorrowDAO;
import dataaccess.concretes.PenaltyDAO;
import dataaccess.concretes.PersonDAO;
import entities.concretes.Book;
import entities.concretes.Borrow;
import entities.concretes.Penalty;
import entities.concretes.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class BorrowInfoManager implements MenuService {
    private BorrowDAO borrowDAO;
    private BookDAO bookDAO;
    private PersonDAO personDAO;
    private PenaltyDAO penaltyDAO;

    private List<Book> bookList;
    private List<Person> personList;
    private List<Borrow> borrowList;
    private List<Penalty> penaltyList;

    private Scanner inp;

    public BorrowInfoManager(BorrowDAO borrowDAO, BookDAO bookDAO, PersonDAO personDAO, PenaltyDAO penaltyDAO, Scanner scanner) {
        this.borrowDAO = borrowDAO;
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.penaltyDAO = penaltyDAO;

        this.bookList = bookDAO.getData();
        this.personList = personDAO.getData();
        this.borrowList = borrowDAO.getData();
        this.penaltyList = penaltyDAO.getData();

        this.inp = scanner;

        checkPenalties();
    }

    @Override
    public void showProcessMenu() {

        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 1 > BORROW BOOK" + " ".repeat(12) + "|" + "\t\t" + "|\t< 2 > RETURN BOOK" + " ".repeat(12) + "|");
            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 3 > LIST BORROWS" + " ".repeat(11) + "|" + "\t\t" + "|\t< 4 > LIST PENALTIES" + " ".repeat(9) + "|");
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
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    listBorrows();
                    break;
                case 4:
                    listPenalties();
                    break;
                default:
                    System.out.println("\t\tInvalid Selection");
                    break;
            }
        }

    }

    private void borrowBook() {
        Person person = findPerson(takePersonId());
        if (person != null) {

            boolean isPenalized = person.isPenalized();
            if (isPenalized) {
                System.out.println("This person has been penalized until " + getDateOfEndOfPenalty(person));
            } else {
                Book book = findBook(takeBookId());
                if (book != null) {

                    String bookStatus = book.getBookStatus().toString();
                    if (bookStatus.equalsIgnoreCase("AVAILABLE")) {
                        LocalDate endDate = LocalDate.now().plusMonths(2);
                        Borrow borrow = new Borrow(person, book, endDate);
                        borrowList.add(borrow);
                        book.setBookStatus(BookStatus.BORROWED);
                        System.out.println("\t\tSuccessful. This book is borrowed until " + endDate + " now. Timeout is penalized");
                    } else {
                        System.out.println("\t\tThis book is not available");
                    }

                } else {
                    System.out.println("\t\tThis book is not available");
                }
            }
        }
    }

    private void returnBook() {
        Person person = findPerson(takePersonId());
        if (person != null) {
            Book book = findBook(takeBookId());
            if (book != null) {
                if (person != null && book != null) {

                    Borrow borrow = new Borrow();
                    borrow.setPerson(person);
                    borrow.setBook(book);

                    if (borrowList.contains(borrow)) {
                        Borrow found = borrowList.get(borrowList.indexOf(borrow));

                        int selection = -1;
                        do {
                            System.out.println("\t\t+" + "-".repeat(68) + "+");
                            System.out.println("\t\t|    < 1 > RETURN      |     < 2 > MISSING    |     < 3 > PASSIVE    |");
                            System.out.println("\t\t+" + "-".repeat(68) + "+");
                            System.out.print("\t\tSELECT : ");
                            selection = InputUtilities.scanInt();
                        } while (selection != 1 && selection != 2 && selection != 3);

                        if (selection == 1) {
                            book.setBookStatus(BookStatus.AVAILABLE);
                            long dateDiff = ChronoUnit.DAYS.between(LocalDate.now(), found.getReturnDate());
                            if (dateDiff < 0) {
                                found.setReturned(true);
                                Penalty penalty = new Penalty(person, LocalDate.now().plusMonths(2L));
                                penaltyList.add(penalty);
                                person.setPenalized(true);
                                System.out.println("\t\tBook return is successful.\nBut the person is penalized for two months because of the timeout.");
                            } else {
                                found.setReturned(true);
                                System.out.println("\t\tBook return is successful");
                            }

                        } else if (selection == 2) {
                            found.setReturned(true);
                            book.setBookStatus(BookStatus.MISSING);
                            Penalty penalty = new Penalty(person, LocalDate.now().plusYears(1L));
                            penaltyList.add(penalty);
                            person.setPenalized(true);
                            System.out.println("\t\tThe person is penalized for one year because of the missing book.");
                        } else {
                            found.setReturned(true);
                            book.setBookStatus(BookStatus.PASSIVE);
                            Penalty penalty = new Penalty(person, LocalDate.now().plusYears(1L));
                            penaltyList.add(penalty);
                            person.setPenalized(true);
                            System.out.println("\t\tThe person is penalized for one year because of the book status.");
                        }

                    } else {
                        System.out.println("\t\tNo borrow record found for this person or this book");
                    }

                } else {
                    System.out.println("\t\tNo borrow record found for this person or this book");
                }
            }
        }
    }

    private void listBorrows() {
        System.out.println("\t\t" + "+" + "-".repeat(142) + "+");
        System.out.printf("\t\t| %-11s | %-20s | %-20s | %-16s | %-20s | %-12s | %-12s | %-8s |\n", "PERSON' ID", "NAME", "LASTNAME", "BOOK ID", "BOOK NAME", "BORROW DATE", "RETURN DATE", "RETURNED");
        System.out.println("\t\t" + "+" + "-".repeat(142) + "+");
        for (Borrow borrow : this.borrowList) {
            if (!borrow.isReturned()) {
                System.out.printf("\t\t| %-11s | %-20s | %-20s | %-16s | %-20s | %-12s | %-12s | %-8s |\n",
                        borrow.getPerson().getPersonId(), borrow.getPerson().getName(), borrow.getPerson().getLastname(), borrow.getBook().getBookId(), borrow.getBook().getBookName(), borrow.getBorrowDate(), borrow.getReturnDate(), borrow.isReturned());

            }
        }
        System.out.println("\t\t" + "+" + "-".repeat(142) + "+");

    }

    private void listPenalties() {
        System.out.println("\t\t" + "+" + "-".repeat(114) + "+");
        System.out.printf("\t\t| %-11s | %-20s | %-20s | %-18s | %-18s | %-10s |\n", "PERSON' ID", "NAME", "LASTNAME", "PENALTY START DATE", "PENALTY END DATE", "ACTIVE");
        System.out.println("\t\t" + "+" + "-".repeat(114) + "+");
        for (Penalty penalty : this.penaltyList) {
            if (penalty.getActive()) {
                System.out.printf("\t\t| %-11s | %-20s | %-20s | %-18s | %-18s | %-10s |\n",
                        penalty.getPerson().getPersonId(), penalty.getPerson().getName(), penalty.getPerson().getLastname(), penalty.getStart(), penalty.getEnd(), penalty.getActive());
            }
        }
        System.out.println("\t\t" + "+" + "-".repeat(114) + "+");
    }

    private Long takePersonId() {
        System.out.printf("\t\t%-14s : ", "Person ID");
        long id = InputUtilities.scanLong();
        if ((int) Math.log10(id) == 10) {
            return id;
        } else {
            System.out.println("\t\tThat's not a ID number! Try Again");
            return takePersonId();
        }
    }

    private Person findPerson(Long personId) {
        Person found = null;
        if (personList.contains(new Person(personId))) {
            found = personList.get(personList.indexOf(new Person(personId)));
        } else {
            System.out.println("\t\tPerson cannot found");
        }
        return found;
    }

    private LocalDate getDateOfEndOfPenalty(Person person) {
        Penalty search = new Penalty();
        search.setPerson(person);

        LocalDate endDate = null;
        if (penaltyList.contains(search)) {
            Penalty found = penaltyList.get(penaltyList.indexOf(search));
            endDate = found.getEnd();
        } else {
            System.out.println("\t\tNo penalty found for this person");
        }
        return endDate;
    }

    private Book findBook(String bookId) {
        Book found = null;
        if (bookList.contains(new Book(bookId))) {
            found = bookList.get(bookList.indexOf(new Book(bookId)));
        } else {
            System.out.println("\t\tBook cannot found");
        }
        return found;
    }

    private String takeBookId() {
        System.out.printf("\t\t%-14s : ", "Book ID");
        return inp.nextLine().toUpperCase().trim();
    }

    public void checkPenalties() {
        for (Penalty penalty : penaltyList) {
            if (penalty.getActive()) {
                if (ChronoUnit.DAYS.between(penalty.getEnd(), LocalDate.now()) == 0) {
                    penalty.setActive(false);
                    Person person = personList.get(personList.indexOf(penalty.getPerson()));
                    person.setPenalized(false);
                }
            }
        }
    }

}
