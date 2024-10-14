package org.alexander.project.utilities;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseUtils {
    public static String url = "jdbc:postgresql://localhost:5432/postgres";
    public static String user = "user";
    public static String password = "password";
    static Statement stmtContainer = null;
    static Statement stmt = null;
    Connection con = null;

    @SneakyThrows
    public void setConnection() {
        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
    }

    @SneakyThrows
    public void execute(String sql) {
        stmt.execute(sql);
    }

    @SneakyThrows
    public ResultSet executeQuery(String sql) {
        return stmt.executeQuery(sql);
    }

    @SneakyThrows
    public void createPersonTable() {
        stmt.execute("create table if not exists person (ID int, name varchar(255), age int, Email varchar(255))");
    }

    @SneakyThrows
    public void dropPersonTable() {
        stmt.execute("drop table if exists person");
    }

    @SneakyThrows
    public ResultSet getPersonTable() {
        return stmt.executeQuery("select * from person");
    }

    @SneakyThrows
    public void insertPerson(int id, String name, int age, String email) {
        stmt.execute("insert into person (ID, name, age, email) values ('" + id + "', '" + name + "', '" + age + "', '" + email + "')");
    }
}