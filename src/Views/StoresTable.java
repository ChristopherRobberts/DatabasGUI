package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class StoresTable {
    private JPanel mainPanel;
    private JTable stores;
    private JLabel status;

    public void addToTable(ArrayList<ArrayList<String>> arr, String game) {
        String[] foo = {"Adress", "Ort", "Saldo"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(foo, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.stores.setModel(model);

        if (stores.getRowCount() < 1) {
            status.setText("\"" + game + "\"" + " is available in all the stores, please make sure your spelling is correct");
        } else {
            status.setText("\"" + game + "\"" + " is not available in the following stores");
        }
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }
}
