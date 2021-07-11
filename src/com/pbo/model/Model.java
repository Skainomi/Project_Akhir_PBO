package com.pbo.model;

import java.sql.*;
import java.util.ArrayList;

abstract class Model {

    final String DBUrl = "jdbc:mysql://localhost:3306/trading_plan?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String DBUsername = "root";
    final String DBPassword = "";
    public Connection koneksi;
    public Statement statement;

    public Model() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        koneksi = DriverManager.getConnection(DBUrl,DBUsername,DBPassword);
        statement = koneksi.createStatement();
    }

    public abstract void update(ArrayList<String> data) throws SQLException;

    public abstract void insert(ArrayList<String> data) throws SQLException;

    public abstract void delete(String data) throws SQLException;

    public abstract ResultSet get(int id) throws SQLException;
}
