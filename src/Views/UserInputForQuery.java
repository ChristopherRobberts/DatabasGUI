package Views;

import Integration.DataBaseConnection;

import javax.swing.*;
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

    public UserInputForQuery(DataBaseConnection conn) {
        sendInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                StoresTable storesTable = new StoresTable();
                String product = userInput.getText();
                ArrayList<ArrayList<String>> arr = conn.showStoresWithoutGivenProduct(product);
                storesTable.addToTable(arr, product);
                frame.setContentPane(storesTable.getPanel());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public JPanel getStores () {
        return this.mainPanel;
    }
}
