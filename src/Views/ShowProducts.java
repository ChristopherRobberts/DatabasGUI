package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ShowProducts {
    private JPanel products;
    private JTable productTable;

    public void addToTable(ArrayList<ArrayList<String>> arr) {
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("Produkt id");
        columnNames.add("Titel id");
        columnNames.add("Streckkod");
        columnNames.add("Pris");
        columnNames.add("Spel serie");
        columnNames.add("spelutvecklare");
        columnNames.add("Plattform");
        String[] foo = columnNames.toArray(new String[columnNames.size()]);
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
