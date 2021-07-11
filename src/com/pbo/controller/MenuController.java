package com.pbo.controller;

import com.pbo.Main;
import com.pbo.model.TradingPlan;
import com.pbo.view.CRUDView;
import com.pbo.view.MenuView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuController implements MainController {

    private final MenuView view;
    private TradingPlan tradingPlan;


    private final String[] kolomDB = {"id", "tanggal", "uang", "emiten", "buy", "take_profit", "cutloss"};
    private final String[] namaKolom = {"No", "Tanggal", "Jumlah Uang", "Emiten", "Buy", "Tp", "Cl", "Profit", "Loss"};
    private ArrayList<Integer> storedId;
    private ArrayList<String> storedTableData;
    private boolean isDialog = false;
    private CRUDView crudView;

    public MenuView getView() {
        return view;
    }

    public void setDialog(boolean dialog) {
        isDialog = dialog;
    }

    public MenuController(MenuView view) {
        try {
            tradingPlan = new TradingPlan();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        this.view = view;
        view.getSt_title().setText("Trading Plan " + view.getUsername());
    }

    @Override
    public void index() {
        for (String s : namaKolom) {
            this.view.getModelTableData().addColumn(s);
        }
        try {
            showTableData(view.getModelTableData());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getClickedData(MenuView view) {
        ArrayList<JButton> buttons = view.getAllButtons();
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
        storedTableData = new ArrayList<>(Arrays.asList(
                String.valueOf(storedId.get(view.getTb_data().getSelectedRow())),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 1)),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 2)),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 3)),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 4)),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 5)),
                String.valueOf(view.getModelTableData().getValueAt(view.getTb_data().getSelectedRow(), 6))
        ));
    }

    private void showTableData(DefaultTableModel model) throws SQLException {
        model.setRowCount(0);
        ArrayList<Integer> storedId = new ArrayList<>();
        ResultSet resultSet = tradingPlan.get(view.getIdUser());
        Object[] row = new Object[9];
        int iteration = 1;
        while (resultSet.next()) {
            row[0] = iteration;
            storedId.add(resultSet.getInt("id"));
            for (int i = 1; i < kolomDB.length; i++) {
                if (i == 2) {
                    row[i] = "Rp. " + new DecimalFormat("#,###.00").format(Double.parseDouble(resultSet.getString(kolomDB[i])));
                } else {
                    row[i] = resultSet.getString(kolomDB[i]);
                }
            }
            row[kolomDB.length] = "Rp. " + new DecimalFormat("#,###.00").format(resultSet.getDouble("uang") * (double) (resultSet.getInt("take_profit") - resultSet.getInt("buy")) / resultSet.getInt("buy"));
            row[kolomDB.length + 1] = "Rp. " + new DecimalFormat("#,###.00").format(resultSet.getDouble("uang") * (double) (resultSet.getInt("buy") - resultSet.getInt("cutloss")) / resultSet.getInt("buy"));
            this.storedId = storedId;
            model.addRow(row);
            iteration += 1;
        }
    }

    @Override
    public void update() {
        if (!isDialog) {
            crudView = new CRUDView(this, 1);
            ArrayList<JTextField> jTextFields = crudView.getAllTextFields();
            for (int i = 0; i < jTextFields.size(); i++) {
                jTextFields.get(i).setText(storedTableData.get(i + 1));
            }
            isDialog = true;
            return;
        }
        try {
            ArrayList<JTextField> textFields = crudView.getAllTextFields();
            ArrayList<String> data = getInput(textFields);
            data.add(String.valueOf(storedTableData.get(0)));
            tradingPlan.update(data);
            showTableData(view.getModelTableData());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        crudView.dispose();
        isDialog = false;
    }

    private ArrayList<String> getInput(ArrayList<JTextField> jTextFields) {
        ArrayList<String> data = new ArrayList<>();
        for (JTextField textField : jTextFields) {
            data.add(textField.getText());
        }
        return data;
    }

    public void enableButton() {
        ArrayList<JButton> buttons = view.getAllButtons();
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    @Override
    public void destroy() {
        int pilihan = JOptionPane.showConfirmDialog(null,
                "Delete Data ? ",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            try {
                tradingPlan.delete(storedTableData.get(0));
                showTableData(view.getModelTableData());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        ArrayList<JButton> jButtons = view.getAllButtons();
        for (JButton jButton : jButtons) {
            jButton.setEnabled(true);
        }
    }

    @Override
    public void insert() {
        if (!isDialog) {
            crudView = new CRUDView(this, 0);
            isDialog = true;
            return;
        }
        ArrayList<JTextField> textFields = crudView.getAllTextFields();
        ArrayList<String> data = getInput(textFields);
        data.add(String.valueOf(view.getIdUser()));
        try {
            tradingPlan.insert(data);
            showTableData(view.getModelTableData());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        isDialog = false;
        crudView.dispose();

    }

    public ArrayList<String> checkInput(CRUDView crudView) {
        ArrayList<JTextField> jTextFields = crudView.getAllTextFields();
        ArrayList<String> desc = new ArrayList<>(Arrays.asList(
                "tanggal",
                "uang",
                "emiten",
                "buy",
                "Tp",
                "Cl"
        ));
        for (int i = 0; i < jTextFields.size(); i++) {
            if (jTextFields.get(i).getText().equals("")) {
                return new ArrayList<>(Arrays.asList(
                        "Input " + desc.get(i) + " Kosong!",
                        "Empty Input"
                ));
            } else if (i == 1 || i > 2) {
                try {
                    Integer.parseInt(jTextFields.get(i).getText());
                } catch (Exception e) {
                    return new ArrayList<>(Arrays.asList(
                            "Input " + desc.get(i) + " harus Berupa Angka!",
                            "False Input Type"
                    ));
                }
            }
        }
        if (Integer.parseInt(jTextFields.get(3).getText()) > Integer.parseInt(jTextFields.get(4).getText())) {
            return new ArrayList<>(Arrays.asList(
                    "Harga Buy harus lebih kecil dari harga Tp!",
                    "False Input"
            ));
        }
        if (Integer.parseInt(jTextFields.get(3).getText()) < Integer.parseInt(jTextFields.get(5).getText())) {
            return new ArrayList<>(Arrays.asList(
                    "Harga Buy harus lebih besar dari harga Cl!",
                    "False Input"
            ));
        }
        return null;
    }

    public void logout(MenuView view) {
        view.dispose();
        Main.getLoginView().setVisible(true);
    }
}
