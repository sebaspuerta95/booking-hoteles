package src.main.java.appStart;

import src.main.java.utils.*;
import src.main.java.utils.facades.MainMethodFacade;

public class Main {

    public static void main(String[] args) {
        // A facade was added to exercise the usage of this pattern.
        new MainMethodFacade().generateHotelsConsult();
    }

}
