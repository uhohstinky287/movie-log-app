package ui;

import java.io.FileNotFoundException;

//LAST SUBMITTED COMMIT
public class Main {

    public static void main(String[] args) {
        try {
//            new MovieLogApp();
            new MovieLogAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
