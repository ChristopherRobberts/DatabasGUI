package Views;

import Integration.DataBaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowProducts {
    private JPanel products;
    private JTable productTable;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTable table1;
    private JButton button1;

    public ShowProducts(DataBaseConnection conn) {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductsWithGivenParameters prod = new ProductsWithGivenParameters();
                JFrame jFrame = new JFrame();
                jFrame.setContentPane(prod.getPanel1());
                jFrame.setDefaultCloseOperation(jFrame.HIDE_ON_CLOSE);
                prod.addToTable(conn.productsWithGenreOrPlattform(textArea1.getText(), textArea2.getText()));
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
    }

    public void addToTable(ArrayList<ArrayList<String>> arr) {
        String[] columns = {"Produkt id", "Pris", "Spelserie", "Spelutvecklare", "Plattform"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.productTable.setModel(model);
    }

    public void addToGenreTable(ArrayList<ArrayList<String>> arr) {
        String[] columns = {"Genre"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.table1.setModel(model);
    }

    public JPanel getProducts() {
        return this.products;
    }
}
