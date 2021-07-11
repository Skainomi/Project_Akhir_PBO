package com.pbo.view;

import com.pbo.controller.MenuController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuView extends JFrame implements ActionListener, MouseListener {
    private JPanel MainPanel;
    private JTable tb_data;
    private JButton btn_add;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_logout;
    private JLabel st_title;
    private final String username;
    private final int idUser;
    ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(
            btn_add,
            btn_update,
            btn_delete,
            btn_logout
    ));

    MenuController menuController;

    public String getUsername() {
        return username;
    }

    public int getIdUser() {
        return idUser;
    }

    public JLabel getSt_title() {
        return st_title;
    }

    public MenuView getView() {
        return this;
    }

    public JTable getTb_data() {
        return tb_data;
    }

    public ArrayList<JButton> getAllButtons() {
        return buttons;
    }

    public MenuView(String title, String username, int idUser) {
        super(title);
        this.username = username;
        this.idUser = idUser;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentPane(MainPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        tb_data.setDefaultEditor(Object.class, null);
        menuController = new MenuController(this);
        for (int i = 0; i < buttons.size(); i++) {
            if (i != 0 && i != 3) {
                buttons.get(i).setEnabled(false);
            }
            buttons.get(i).addActionListener(this);
        }
        menuController.index();
        tb_data.addMouseListener(this);
    }

    public DefaultTableModel getModelTableData() {
        return (DefaultTableModel) tb_data.getModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        if (btn_add.equals(e.getSource())) {
            menuController.insert();
        } else if (btn_delete.equals(e.getSource())) {
            menuController.destroy();
        } else if (btn_update.equals(e.getSource())) {
            menuController.update();
        } else if (btn_logout.equals(e.getSource())) {
            menuController.logout(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(tb_data)) {
            menuController.getClickedData(getView());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
