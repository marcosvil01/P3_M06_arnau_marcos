package Exercici_01.Resources;

import java.io.FileWriter;
import java.io.IOException;


public class writeToFile {
    public static void writeToFile(String content, String fileName) {
        try (FileWriter writer = new FileWriter("output/" + fileName)) {
            writer.write(content);
            System.out.println("El document XML s'ha creat correctament: " + fileName);
        } catch (IOException e) {
            System.err.println("Error escrivint el document XML: " + e.getMessage());
        }
    }
}
