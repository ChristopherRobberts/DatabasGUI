import Integration.DataBaseConnection;
import Views.DBapp;

import javax.swing.*;
public class Main {
    public static void main (String[] args) {
        DataBaseConnection con = new DataBaseConnection();
        con.connectDataBase();
        JFrame frame = new JFrame();
        DBapp databas = new DBapp();
        frame.setContentPane(databas.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
