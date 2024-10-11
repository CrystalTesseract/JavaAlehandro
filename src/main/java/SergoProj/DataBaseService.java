package SergoProj;

import lombok.SneakyThrows;

import java.sql.*;

public class DataBaseService {
    public static String url = "jdbc:postgresql://localhost:5432/postgres";
    public static String user = "user";
    public static String password = "password";
    Statement stmt = null;

    @SneakyThrows
    public void connect(){
        Connection con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
    }
    @SneakyThrows
    public void execute(String sql) {
        stmt.execute(sql);
    }
    @SneakyThrows
    public ResultSet executeQuery(String sql) {
        ResultSet pashalco = stmt.executeQuery(sql);
        return pashalco;
    }

}
