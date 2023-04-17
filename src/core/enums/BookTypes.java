package core.enums;

import core.exceptions.BookTypesIndexOutOfBoundsException;

public enum BookTypes {

    ADVENTURE,
    CLASSICS,
    CRIME,
    FAIRY_TALES,
    FANTASY,
    HISTORICAL,
    HORROR,
    HUMOUR,
    MYSTERY,
    POETRY,
    SCIENCE_FICTION,
    WAR,
    BIOGRAPHY,
    ESSAYS,
    NOVEL,
    ART;
    private static final BookTypes[] TYPE_ENUMS = BookTypes.values();

    public static BookTypes of(int type) {
        if (type < 1 || type > TYPE_ENUMS.length) {
            throw new BookTypesIndexOutOfBoundsException("Invalid value for Department: " + type);
        }
        return TYPE_ENUMS[type - 1];
    }

    public static void toList() {
        System.out.print("\t\t+" + "-".repeat(115) + "+\n\t\t");
        for (int i = 0; i < TYPE_ENUMS.length; i++) {
            System.out.printf("|" + " < %2d >" + " %-20s", (i + 1), TYPE_ENUMS[i]);
            if ((i + 1) % 4 == 0) System.out.print("|\n\t\t");
        }
        System.out.println("+" + "-".repeat(115) + "+");
    }

    public static int size() {
        return TYPE_ENUMS.length;
    }
}
