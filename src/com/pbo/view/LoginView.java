package com.pbo.view;

import com.pbo.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginView extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JTextField et_username;
    private JTextField et_password;
    private JButton btn_login;
    private JButton btn_register;
    UserController userController;

    private final ArrayList<JTextField> textFields = new ArrayList<>(Arrays.asList(
            et_username,
            et_password
    ));

    public LoginView(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentPane(MainPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        userController = new UserController(this);
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(
                btn_login,
                btn_register
        ));
        for (JButton button : buttons){
            button.addActionListener(this);
        }
    }

    public ArrayList<JTextField> getAllTextField(){
        return textFields;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!userController.validateInput())
            return;
        if (btn_login.equals(e.getSource())) {
            userController.login();
        } else if (btn_register.equals(e.getSource())) {
            userController.insert();
        }
    }
}
