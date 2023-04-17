package application;

import business.abstracts.MenuService;
import business.concretes.BookInfoManager;
import business.concretes.BorrowInfoManager;
import business.concretes.PersonInfoManager;
import core.utilities.InputUtilities;
import dataaccess.concretes.BookDAO;
import dataaccess.concretes.BorrowDAO;
import dataaccess.concretes.PenaltyDAO;
import dataaccess.concretes.PersonDAO;

import java.time.LocalDate;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        BookDAO bookDAO = new BookDAO();
        PersonDAO personDAO = new PersonDAO();
        BorrowDAO borrowDAO = new BorrowDAO();
        PenaltyDAO penaltyDAO = new PenaltyDAO();


        Thread readBook = new Thread(bookDAO::read);
        Thread readPerson = new Thread(personDAO::read);
        Thread readBorrow = new Thread(borrowDAO::read);
        Thread readPenalty = new Thread(penaltyDAO::read);
        readBook.start();
        readPerson.start();
        readBorrow.start();
        readPenalty.start();

        int select = -1;
        while (select != 0) {

            showStartMenu();
            select = InputUtilities.scanInt();

            MenuService ms;

            switch (select) {
                case 1:
                    ms = new BookInfoManager(bookDAO, scanner);
                    ms.showProcessMenu();
                    break;
                case 2:
                    ms = new PersonInfoManager(personDAO, scanner);
                    ms.showProcessMenu();
                    break;
                case 3:
                    ms = new BorrowInfoManager(borrowDAO, bookDAO, personDAO, penaltyDAO, scanner);
                    ms.showProcessMenu();
                    break;
                case 0:
                    Thread writeBook = new Thread(bookDAO::write);
                    Thread writePerson = new Thread(personDAO::write);
                    Thread writeBorrow = new Thread(borrowDAO::write);
                    Thread writePenalty = new Thread(penaltyDAO::write);
                    writeBook.start();
                    writePerson.start();
                    writeBorrow.start();
                    writePenalty.start();

                    System.out.println("\n\t\tSTAY WITH BOOKS");
                    break;
                default:
                    System.out.println("\t\tInvalid Selection. Try Again");
                    break;
            }
        }


    }

    private static void showStartMenu() {
        System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
        System.out.println("\t\t" + "|\t\t< 1 > BOOKS" + " ".repeat(14) + "|" + "\t\t" + "|\t\t< 2 > PERSON" + " ".repeat(13) + "|");
        System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

        System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
        System.out.println("\t\t" + "|\t\t< 3 > BORROW" + " ".repeat(13) + "|" + "\t\t" + "|\t\t" + "< 0 > EXIT" + " ".repeat(15) + "|");
        System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

        System.out.print("\t\tSELECT : ");
    }
}
