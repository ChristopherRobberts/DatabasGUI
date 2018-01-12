package Views;

import Integration.DataBaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Chris on 2017-12-30.
 */
public class UserInputForQuery {
    private JTextField userInput;
    private JPanel mainPanel;
    private JButton sendInput;
    private JTable table1;

    public UserInputForQuery(DataBaseConnection conn) {
        sendInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                StoresTable storesTable = new StoresTable();
                try {
                    String product = userInput.getText();
                    ArrayList<ArrayList<String>> arr = conn.showStoresWithoutGivenProduct(Integer.parseInt(product));
                    storesTable.addToTable(arr, product);
                    frame.setContentPane(storesTable.getPanel());
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                } catch(Exception ex) {
                    userInput.setText("you must enter an integer value, please try again");
                }
            }
        });
    }
    public void addProductsToTable(ArrayList<ArrayList<String>> arr) {
        String[] columns = {"Produkt id", "Pris", "Spelserie", "Spelutvecklare", "Plattform"};
        String[][] content = new String[arr.size()][];
        DefaultTableModel currentModel = new DefaultTableModel(columns, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            content[i] = row.toArray(new String[arr.get(i).size()]);
            currentModel.addRow(content[i]);
        }
        this.table1.setModel(currentModel);
    }

    public JPanel getStores () {
        return this.mainPanel;
    }
}
