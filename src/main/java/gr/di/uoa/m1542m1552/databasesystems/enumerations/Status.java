package gr.di.uoa.m1542m1552.databasesystems.enumerations;

public enum Status {
    COMPLETED("Completed"),
    OPEN("Open"),
    OPEN_DUP("Open_Dup"),
    COMPLETED_DUP("Completed_Dup");

    private String text;

    Status(String text) {
      this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Status fromString(String text) {
        for (Status b : Status.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
  
}