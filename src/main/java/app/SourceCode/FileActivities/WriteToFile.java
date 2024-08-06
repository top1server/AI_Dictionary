package app.SourceCode.FileActivities;

import app.SourceCode.Fundamental.Word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteToFile {
    public static void writeTXT(String filePath) {
        try {
            // Open file
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            // Close file
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: IO Exception when writing to txt!");
        }
    }
    public static void writeTXT(String filePath, String word) {
        try {
            // Open file
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(word);
            bufferedWriter.newLine();
            // Close file
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: IO Exception when writing to txt!");
        }
    }
}
