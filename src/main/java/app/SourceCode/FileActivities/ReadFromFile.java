package app.SourceCode.FileActivities;
import app.SourceCode.Fundamental.Word;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReadFromFile {
    /**
     * read txt.
     * @param filePath String
     * @return List<Word>
     */
    public static List<Word> readTXT(String filePath) {
        List<Word> ans = null;
        try {
            ans = new ArrayList<>();
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("\n")) continue;
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String word_target = parts[0];
                    String word_explain = parts[1];
                    Word newWord = new Word(word_target, word_explain);
                    ans.add(newWord);
                }
            }
            // Close
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File.txt not found!");
        } catch (IOException e) {
            System.out.println("Error: IO Exception when read txt!");
        }
        return ans;
    }

    /**
     * read sql.
     * @return List<Word>
     */
    public static List<Word> readSQL() {
        List<Word> ans = null;
        String url = "jdbc:mysql://localhost:3306/dictionary";
        String username = "root";
        String password = "anhduong97protb";

        try {
            ans = new ArrayList<>();
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Loading dictionary ...");

                // Query data
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT target, definition FROM dictionary");

                //Loop for read file
                while(resultSet.next()) {
                    String word_target = resultSet.getString("target")
                            .replaceAll("-", "")
                            .replaceAll("," , "")
                            .replaceAll("\\.", " ")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll("=", "");
                    String word_explain = resultSet.getString("definition");
                    word_explain = word_explain
                            .replaceAll("<I><Q>", "")
                            .replaceAll("</Q></I>", "")
                            .replaceAll("<br />", "\n")
                            .replaceAll("\\+", " :")
                            .replaceAll("=", "~ ")
                            .replaceAll("!", "+ ");
                    Word newWord = new Word(word_target, word_explain);

                    ans.add(newWord);
                }

                //Close
                resultSet.close();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error: SQL Exception!");
        }
        return ans;
    }
}
