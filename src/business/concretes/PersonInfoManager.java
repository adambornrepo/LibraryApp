package business.concretes;

import business.abstracts.MenuService;
import core.utilities.InputUtilities;
import dataaccess.concretes.PersonDAO;
import entities.concretes.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PersonInfoManager implements MenuService {

    private PersonDAO personDAO;

    private List<Person> personList;
    private Scanner inp;

    public PersonInfoManager(PersonDAO personDAO, Scanner inp) {
        this.personDAO = personDAO;
        this.personList = personDAO.getData();
        this.inp = inp;
    }

    @Override
    public void showProcessMenu() {

        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 1 > ADD PERSON" + " ".repeat(13) + "|" + "\t\t" + "|\t< 2 > UPDATE PERSON'S INFO" + " ".repeat(3) + "|");
            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println("\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println("\t\t" + "|\t< 3 > FIND PERSON" + " ".repeat(12) + "|" + "\t\t" + "|\t< 4 > LIST ALL" + " ".repeat(15) + "|");
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
                    addPerson();
                    break;
                case 2:
                    updatePersonInfo(takePersonId());
                    break;
                case 3:
                    findPerson(takePersonId());
                    break;
                case 4:
                    listAll();
                    break;
                default:
                    System.out.println("\t\tInvalid Selection");
                    break;
            }
        }

    }

    private void addPerson() {

        Person person = createPerson();
        boolean added = personList.add(person);

        if (added) {
            System.out.println("\t\tNew person successfully added");
        } else {
            System.out.println("\t\tThis person cannot be added");
        }

    }


    private void updatePersonInfo(Long personId) {

        int updateSelection = -1;
        do {
            System.out.println("\t\t+" + "-".repeat(72) + "+");
            System.out.println("\t\t|   < 1 > UPDATE JUST PHONE NUMBER   |   < 2 > UPDATE ALL INFORMATION    |");
            System.out.println("\t\t+" + "-".repeat(72) + "+");
            System.out.print("\t\tSELECT : ");
            updateSelection = InputUtilities.scanInt();
        } while (updateSelection != 1 && updateSelection != 2);

        if (updateSelection == 1) {

            if (personList.contains(new Person(personId))) {
                Person found = personList.get(personList.indexOf(new Person(personId)));

                System.out.printf("\t\t%-14s : ", "Phone number");
                String phoneNumber = inp.nextLine().trim().toUpperCase();

                found.setPhoneNumber(phoneNumber);
                System.out.println("\t\tPerson' phone number updated");
            } else {
                System.out.println("\t\tPerson cannot found");
            }

        } else {

            if (personList.contains(new Person(personId))) {
                Person found = personList.get(personList.indexOf(new Person(personId)));

                Person person = createPerson();

                found.setPersonId(person.getPersonId());
                found.setName(person.getName());
                found.setLastname(person.getLastname());
                found.setPhoneNumber(person.getPhoneNumber());
                found.setMail(person.getMail());
                found.setGender(person.getGender());
                found.setBirthdate(found.getBirthdate());

                System.out.println("\t\tPerson' information updated");
            } else {
                System.out.println("\t\tPerson cannot found");
            }

        }

    }

    private void findPerson(Long personId) {

        if (personList.contains(new Person(personId))) {
            Person found = personList.get(personList.indexOf(new Person(personId)));

            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
            System.out.printf("\t\t| %-11s | %-20s | %-20s | %-15s | %-25s | %-10s | %-11s | %-9s |\n", "PERSON' ID", "NAME", "LASTNAME", "PHONE NUMBER", "EMAIL", "BIRTHDATE", "GENDER", "PENALIZED");
            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
            System.out.printf("\t\t| %-11s | %-20s | %-20s | %-15s | %-25s | %-10s | %-11s | %-9s |\n",
                    found.getPersonId(), found.getName(), found.getLastname(), found.getPhoneNumber(), found.getMail(), found.getBirthdate(), found.getGender(), found.isPenalized());
            System.out.println("\t\t" + "+" + "-".repeat(144) + "+");

        } else {
            System.out.println("\t\tPerson cannot found");
        }

    }

    private void listAll() {

        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
        System.out.printf("\t\t| %-11s | %-20s | %-20s | %-15s | %-25s | %-10s | %-11s | %-9s |\n", "PERSON' ID", "NAME", "LASTNAME", "PHONE NUMBER", "EMAIL", "BIRTHDATE", "GENDER", "PENALIZED");
        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");
        for (Person person : this.personList) {
            System.out.printf("\t\t| %-11s | %-20s | %-20s | %-15s | %-25s | %-10s | %-11s | %-9s |\n",
                    person.getPersonId(), person.getName(), person.getLastname(), person.getPhoneNumber(), person.getMail(), person.getBirthdate(), person.getGender(), person.isPenalized());
        }
        System.out.println("\t\t" + "+" + "-".repeat(144) + "+");

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

    private Person createPerson() {

        Long id = takePersonId();

        System.out.printf("\t\t%-14s : ", "Person Name");
        String name = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Lastname");
        String lastname = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Phone number");
        String phoneNumber = inp.nextLine().trim().toUpperCase();

        System.out.printf("\t\t%-14s : ", "Email");
        String email = inp.nextLine().trim().toLowerCase();

        Character gender = null;
        int selection = -1;
        do {
            System.out.println("\t\t+" + "-".repeat(45) + "+");
            System.out.println("\t\t|   < 1 > MALE         |   < 2 > FEMALE       |");
            System.out.println("\t\t+" + "-".repeat(45) + "+");
            System.out.print("\t\tSELECT : ");
            selection = InputUtilities.scanInt();
        } while (selection != 1 && selection != 2);

        if (selection == 1) {
            gender = 'M';
        } else {
            gender = 'F';
        }

        LocalDate birthdate = InputUtilities.scanDate("Birth");

        Person person = new Person(id, name, lastname, phoneNumber, email, gender, birthdate);

        return person;
    }


}
