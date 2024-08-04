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

            System.out.println("Enter number of words you can add to 'dictionary':");
            Scanner sc = new Scanner(System.in);
            int numWords = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter " + numWords + " word_target and word_explain (separated by a tab):");
            while(numWords != 0) {
                String line = sc.nextLine();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                numWords--;
            }

            // Close file
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Successfully written to file.");
        } catch (IOException e) {
            System.out.println("Error: IO Exception when writing to txt!");
        }
    }
}
