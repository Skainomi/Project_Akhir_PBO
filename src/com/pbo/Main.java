package com.pbo;

import com.pbo.view.LoginView;

import javax.swing.*;

public class Main {
    private static JFrame loginView;

    public static JFrame getLoginView() {
        return loginView;
    }

    public static void main(String[] args) {
        loginView = new LoginView("Login User");
    }
}
