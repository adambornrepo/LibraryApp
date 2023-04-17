package core.utilities;

import java.time.LocalDate;
import java.util.Scanner;

public class InputUtilities {
    private static Scanner scanner = new Scanner(System.in);

    public static int scanInt() {
        int result = Integer.MIN_VALUE;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("\t\tThat's not a number!\n\t\tTry Again : ");
                scanner.next();
            }
            result = scanner.nextInt();
        } while (result == Integer.MIN_VALUE);
        return result;
    }

    public static long scanLong() {
        long result = Long.MIN_VALUE;
        do {
            while (!scanner.hasNextLong()) {
                System.out.print("\t\tThat's not a number!\n\t\tTry Again : ");
                scanner.next();
            }
            result = scanner.nextLong();
        } while (result == Long.MIN_VALUE);
        return result;
    }

    public static LocalDate scanDate(String dateSubject) {
        Scanner scanner1 = new Scanner(System.in);
        String[] param;
        LocalDate date;
        boolean year;
        do {
            boolean matches;
            do {
                System.out.printf("\t\t%-14s : ", dateSubject + " Date");

                String s = scanner1.nextLine().trim();
                matches = s.matches("^[0-9]{1,2}\\s+[0-9]{1,2}\\s+[0-9]{4}$");
                param = s.split("\\s+");

                if (!matches) {
                    System.out.println("\t\tInvalid date. Try Again");
                }

            } while (!matches);

            year = Integer.parseInt(param[2]) < LocalDate.now().getYear() - 120 || Integer.parseInt(param[2]) > LocalDate.now().getYear();

            try {
                if (year) throw new Exception("Invalid value for year (valid values 120 years ago from today - now)");
                date = LocalDate.of(Integer.parseInt(param[2]), Integer.parseInt(param[1]), Integer.parseInt(param[0]));
            } catch (Exception e) {
                System.out.println("\t\t📅 ERROR:" + e.getMessage() + " Try Again");
                return scanDate(dateSubject);
            }
        } while (year);

        return date;
    }


}
