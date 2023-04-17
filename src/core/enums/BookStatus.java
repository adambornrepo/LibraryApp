package core.enums;

import core.exceptions.BookStatusIndexOutOfBoundsException;

public enum BookStatus {

    AVAILABLE,
    BORROWED,
    MISSING,
    PASSIVE;

    private static final BookStatus[] STATUS_ENUMS = BookStatus.values();

    public static BookStatus of(int status) {
        if (status < 1 || status > STATUS_ENUMS.length) {
            throw new BookStatusIndexOutOfBoundsException("Invalid value for Book Status : " + status);
        }
        return STATUS_ENUMS[status - 1];
    }

    public static void toList() {
        System.out.print("\t\t+" + "-".repeat(115) + "+\n\t\t");
        for (int i = 0; i < STATUS_ENUMS.length; i++) {
            System.out.printf("|" + " < %2d >" + " %-20s", (i + 1), STATUS_ENUMS[i]);
            if ((i + 1) % 4 == 0) System.out.print("|\n\t\t");
        }
        System.out.println("+" + "-".repeat(115) + "+");
    }

    public static int size() {
        return STATUS_ENUMS.length;
    }
}
