package com.pbo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TradingPlan extends Model {

    public TradingPlan() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void update(ArrayList<String> data) throws SQLException {
        super.statement.execute("UPDATE `plan` SET `tanggal` = '"+ data.get(0) +"', `uang` = '"+ data.get(1) +"', `emiten` = '"+ data.get(2) +"', `buy` = '"+ data.get(3) +"', `take_profit` = '"+ data.get(4) +"', `cutloss` = '"+ data.get(5) +"' WHERE `plan`.`id` = '"+ data.get(6) +"'");
    }

    @Override
    public void insert(ArrayList<String> data) throws SQLException {
        super.statement.executeUpdate("INSERT INTO `plan` (`id`, `id_user`, `tanggal`, `uang`, `emiten`, `buy`, `take_profit`, `cutloss`) VALUES (NULL,'" + data.get(6) + "', '"+ data.get(0) +"', '" + data.get(1) + "', '" + data.get(2) + "', '" + data.get(3) + "', '" + data.get(4) + "', '"+ data.get(5) +"')");
    }

    @Override
    public void delete(String data) throws SQLException {
        super.statement.execute("DELETE FROM `plan` WHERE `plan`.`id` = '" + data + "'");

    }

    @Override
    public ResultSet get(int id) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM `plan` WHERE `plan`.`id_user` = '"+ id +"'");
        return resultSet;
    }
}
