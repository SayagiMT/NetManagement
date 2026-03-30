package com.NetProject.view;

import com.NetProject.entity.Computer;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ComputerForm extends JInternalFrame {
    public ComputerForm() {
        super("Computer Management System", true, true, true, true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Status", "Prices"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        List<Computer> list = new ArrayList<>();
        list.add(new Computer(1, "VIP 01", "Occupied", 12000));
        list.add(new Computer(2, "Normal 01", "Empty", 6000));
        list.add(new Computer(3, "Normal 02", "Under Maintenance", 6000));

        for (Computer c : list) {
            Object[] rowData = {c.getId(), c.getName(), c.getStatus(), c.getPrices()};
            model.addRow(rowData);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }


}
