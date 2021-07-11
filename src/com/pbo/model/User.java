package com.pbo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends Model {


    public User() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void update(ArrayList<String> data){

    }

    @Override
    public void insert(ArrayList<String> data) throws SQLException {
        super.statement.executeUpdate("INSERT INTO `user` (`id_user`, `username`, `password`) VALUES (NULL, '"+ data.get(0) +"', '"+ data.get(1) +"')");

    }

    @Override
    public void delete(String data) throws SQLException {

    }

    @Override
    public ResultSet get(int id){
        return null;
    }

    public ResultSet search(ArrayList<String> data) throws SQLException {
        return super.statement.executeQuery("SELECT * FROM `user` WHERE `username` = '"+ data.get(0) +"' AND `password` = '"+ data.get(1) +"' ");
    }

}
