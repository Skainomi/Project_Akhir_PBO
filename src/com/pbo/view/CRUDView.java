package com.pbo.view;

import com.pbo.controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CRUDView extends JDialog implements ActionListener {

    private JPanel MainPanel;
    private JTextField et_tanggal;
    private JTextField et_emiten;
    private JTextField et_buy;
    private JTextField et_tp;
    private JButton btn_ok;
    private JButton btn_cancel;
    private JLabel st_title;
    private JTextField et_uang;
    private JTextField et_cl;

    MenuController menuController;
    int status;

    public ArrayList<JTextField> getAllTextFields() {
        return new ArrayList<>(Arrays.asList(
                et_tanggal,
                et_uang,
                et_emiten,
                et_buy,
                et_tp,
                et_cl
        ));
    }

    public CRUDView(MenuController menuController, int status) {
        this.status = status;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MainPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
        this.setContentPane(MainPanel);
        this.setUndecorated(true);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.menuController = menuController;
        switch (status) {
            case 0 -> st_title.setText("Insert Data");
            case 1 -> st_title.setText("Update Data");
            case 2 -> st_title.setText("Delete Data");
        }
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(
                btn_ok,
                btn_cancel
        ));
        for (JButton button : buttons) {
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_ok)) {
            ArrayList<String> msg = menuController.checkInput(this);
            if (msg != null) {
                JOptionPane.showMessageDialog(null,
                        msg.get(0),
                        msg.get(1),
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            menuController.enableButton();
            switch (status) {
                case 0 -> menuController.insert();
                case 1 -> menuController.update();
                case 2 -> menuController.destroy();
            }
        }
        menuController.getView().getAllButtons().get(0).setEnabled(true);
        menuController.getView().getAllButtons().get(3).setEnabled(true);
        menuController.setDialog(false);
        this.dispose();
    }
}
