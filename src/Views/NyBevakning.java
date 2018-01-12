package Views;

import Integration.DataBaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Chris on 2017-12-28.
 */
public class NyBevakning {
    private JPanel nyBevakning;
    private JTextField textField1;
    private JButton query;
    private JTextField textField2;
    private JTable table1;
    private JTable table2;
    private JTextField textField3;

    public NyBevakning(DataBaseConnection dataBaseConnection) {
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int produktID = Integer.parseInt(textField1.getText());
                int butikID = Integer.parseInt(textField2.getText());
                String emailParam = textField3.getText();
                String success = dataBaseConnection.läggTillNyBevakning(produktID, butikID, emailParam);
                JFrame jFrame = new JFrame();
                JLabel jLabel = new JLabel();
                jLabel.setText(success);
                jFrame.setContentPane(jLabel);
                jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
    }

    public JPanel getJpanel() {
        return nyBevakning;
    }

    public void addToProductTable(ArrayList<ArrayList<String>> arr) {
        String[] columns = {"Produkt id", "Pris", "Spelserie", "Spelutvecklare", "Plattform"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.table1.setModel(model);
    }

    public void addToButikTable(ArrayList<ArrayList<String>> arr) {
        String[] foo = {"Butik id", "Adress", "Ort"};
        String[][] bar = new String[arr.size()][];
        DefaultTableModel model = new DefaultTableModel(foo, 0);
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> row = arr.get(i);
            bar[i] = row.toArray(new String[arr.get(i).size()]);
            model.addRow(bar[i]);
        }
        this.table2.setModel(model);
    }
}
