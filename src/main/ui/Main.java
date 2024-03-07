package ui;

import java.io.FileNotFoundException;

//LAST SUMBITTED COMMIT
public class Main {
    public static void main(String[] args) {
        try {
            new MovieLogApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
