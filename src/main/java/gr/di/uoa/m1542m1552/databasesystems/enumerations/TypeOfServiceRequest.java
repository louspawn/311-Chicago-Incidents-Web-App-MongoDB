package gr.di.uoa.m1542m1552.databasesystems.enumerations;

public enum TypeOfServiceRequest {
    ABANDONED_VEHICLE("Abandoned Vehicle Complaint"),
    ALLEY_LIGHTS_OUT("Alley Light Out"),
    GARBAGE_CARTS("Garbage Cart Black Maintenance/Replacement"),
    GRAFFITI_REMOVAL("Graffiti Removal"),
    POT_HOLES("Pot Hole in Street"),
    RODENT_BAITING("Rodent Baiting/Rat Complaint"),
    SANITATION_CODE_COMPLAINTS("Sanitation Code Violation"),
    LIGHTS_ALL_OUT("Street Lights - All/Out"),
    LIGHT_ONE_OUT("Street Light - 1/Out"),
    TREE_DEBRIS("Tree Debris"),
    TREE_TRIMS("Tree Trim");

    private String text;

    TypeOfServiceRequest(String text) {
      this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TypeOfServiceRequest fromString(String text) {
        for (TypeOfServiceRequest b : TypeOfServiceRequest.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
  
}