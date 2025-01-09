package org.alexander.project.repository;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class DataBaseStmtRepository {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "user";
    private static final String password = "password";
    private static Statement stmt = null;

    @SneakyThrows
    public void setConnection() {
        Connection con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
    }

    @SneakyThrows
    public void Zexecute(String sql) {
        stmt.execute(sql);
    }

    @SneakyThrows
    public ResultSet ZexecuteQuery(String sql) {
        return stmt.executeQuery(sql);
    }

    @SneakyThrows
    public void createPersonTable() {
        stmt.execute("create table if not exists person (ID int, name varchar(255), age int, Email varchar(255), inn varchar, organizationData varchar)");
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
    public void insertPerson(int id, String name, int age, String email, String inn) {
        stmt.execute("insert into person (ID, name, age, email, inn) values ('" + id + "', '" + name + "', '" + age + "', '" + email + "', '" + inn + "')");
    }

    @SneakyThrows
    public void insertOrganizationData(int id, String organizationData) {
        stmt.execute("UPDATE person SET organizationData = '" + organizationData + "' WHERE id = " + id);
    }

    @SneakyThrows
    public String getOrganizationData(int id) {
        ResultSet rs = stmt.executeQuery("SELECT organizationData FROM person WHERE id = " + id);
        rs.next();
        return rs.getString("organizationData");
    }

    @SneakyThrows
    public String getInn(int id) {
        ResultSet rs = stmt.executeQuery("SELECT inn FROM person WHERE id = " + id);
        rs.next();
        return rs.getString("inn");
    }
}