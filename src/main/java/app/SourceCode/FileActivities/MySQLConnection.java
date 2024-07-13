package app.SourceCode.FileActivities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dictionary";
        String username = "root";
        String password = "anhduong97protb";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Đã truy cập vào cơ sở dữ liệu 'dictionary'");

                // Truy vấn dữ liệu từ bảng 'dictionary'
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT ID, target FROM dictionary");

                // In ra tiêu đề bảng
                System.out.printf("%-10s| %-30s%n", "No", "English");

                // Bộ đếm để giới hạn số dòng in ra
                int rowCount = 0;
                final int MAX_ROWS = 10; // Thay đổi số lượng dòng in ra

                // In ra dữ liệu của từng hàng, tối đa 10 dòng
                while (resultSet.next() && rowCount < MAX_ROWS) {
                    int id = resultSet.getInt("ID");
                    String target = resultSet.getString("target");

                    // In ra dữ liệu theo định dạng bảng
                    System.out.printf("%-10d| %-30s%n", id, target);

                    rowCount++;
                }

                resultSet.close();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
