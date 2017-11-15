package models.storage;

/**
 * Category of a tuition form defined by Info Support.
 * used internal by Info Support.
 */
public enum TuitionCategory {
    Category_1("Cursussen en studies die je volgt om achterstanden in kennis- c.q. vaardigheden in te lopen, met als doel ingezet te kunnen worden (bij de klant)."),
    Category_2("Alle overige cursussen en studies"),
    Category_3("MBA's en andere zeer kostbare (>€8000");

    private final String value;

    TuitionCategory(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //public String getValue
}
