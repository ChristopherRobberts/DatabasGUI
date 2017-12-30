package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ShowProducts {
    private JPanel products;
    private JTable productTable;

    public void addToTable(ArrayList<ArrayList<String>> arr) {
        String[] foo = {"Produkt id", "Titel id", "Streckkod", "Pris", "Spelserie", "Spelutvecklare", "Plattform"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(foo, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.productTable.setModel(model);
    }
    public JPanel getProducts() {
        return this.products;
    }
}
