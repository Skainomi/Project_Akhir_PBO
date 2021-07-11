package com.pbo.controller;

import com.pbo.model.User;
import com.pbo.view.LoginView;
import com.pbo.view.MenuView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController implements MainController {


    LoginView view;
    User user;

    public UserController(LoginView view) {
        this.view = view;
        try {
            this.user = new User();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void index() {

    }

    @Override
    public void update() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void insert() {
        try {
            user.insert(getInput());
            JOptionPane.showMessageDialog(null,
                    "Register Berhasil!",
                    "Register Berhasil!",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Register Gagal!",
                    "Input Kosong",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void login() {
        try {
            ArrayList<String> data = getInput();
            ResultSet dataLogin = user.search(data);
            if (dataLogin.next()) {
                view.setVisible(false);
                for (JTextField textField : view.getAllTextField()) {
                    textField.setText("");
                }
                new MenuView("Menu", data.get(0),dataLogin.getInt("id_user"));
            } else {
                JOptionPane.showMessageDialog(null, "Username/Password Salah");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean validateInput() {
        ArrayList<JTextField> data = view.getAllTextField();
        final String[] errorMsg = {
                "Mohon isi username!",
                "Mohon isi password!"
        };
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getText().equals("")) {
                JOptionPane.showMessageDialog(null, errorMsg[i],
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getInput() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < view.getAllTextField().size(); i++) {
            data.add(view.getAllTextField().get(i).getText());
        }
        return data;
    }
}
