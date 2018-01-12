package Views;

import Integration.DataBaseConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 2017-12-27.
 */
public class DBapp {
    private JPanel jpanel1;
    private JPanel westLabelPanel;
    private JPanel eastTextFieldPanel;
    private JPanel southButtons;
    private JButton produkterButton;
    private JButton nyBevakningButton;
    private JButton ejTillgängligaProdukterButton;
    private DataBaseConnection connection;

    public DBapp() {
        ejTillgängligaProdukterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame();
                connection = new DataBaseConnection();
                connection.connectDataBase();
                UserInputForQuery stores = new UserInputForQuery(connection);
                stores.addProductsToTable(connection.showProducts());
                jFrame.setContentPane(stores.getStores());
                jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
        produkterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection = new DataBaseConnection();
                connection.connectDataBase();
                ShowProducts showProducts = new ShowProducts(connection);
                JFrame frame = new JFrame();
                frame.setContentPane(showProducts.getProducts());
                showProducts.addToTable(connection.showProducts());
                showProducts.addToGenreTable(connection.getGenre());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        nyBevakningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection = new DataBaseConnection();
                connection.connectDataBase();
                NyBevakning nyBevakning = new NyBevakning(connection);
                JFrame frame = new JFrame();
                frame.setContentPane(nyBevakning.getJpanel());
                nyBevakning.addToButikTable(connection.showButiker());
                nyBevakning.addToProductTable(connection.showProducts());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public JPanel getPanel() {
        return jpanel1;
    }
}
