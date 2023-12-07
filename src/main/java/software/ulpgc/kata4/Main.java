package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Dani/Descargas/SQLiteDBbrowser/newdb.db")) {
            for(Order order : SqliteOrderLoader.with(connection).loadAll()){
                System.out.println(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
