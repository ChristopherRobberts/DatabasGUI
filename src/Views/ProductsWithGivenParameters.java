package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Created by Chris on 2018-01-12.
 */
public class ProductsWithGivenParameters {
    private JTable table1;
    private JPanel panel1;

    public void addToTable(ArrayList<ArrayList<String>> arr) {
        String[] columns = {"Produkt ID", "Speltitel", "Plattform", "Genre"};
        String[][] content = new String[arr.size()][];
        DefaultTableModel currentModel = new DefaultTableModel(columns, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            content[i] = row.toArray(new String[arr.get(i).size()]);
            currentModel.addRow(content[i]);
        }
        this.table1.setModel(currentModel);
    }
    public JPanel getPanel1(){
        return this.panel1;
    }
}
