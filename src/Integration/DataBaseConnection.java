package Integration;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection {
    private Connection conn;

    public void connectDataBase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Chris/Desktop/SpelRvi_Access.accdb");
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    public ArrayList<ArrayList<String>> showProducts() {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produkt");
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            while (rs.next()) {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(rs.getString("produkt_id"));
                innerArray.add(rs.getString("titel_id"));
                innerArray.add(rs.getString("streckkod"));
                innerArray.add(rs.getString("pris"));
                innerArray.add(rs.getString("spelSerie"));
                innerArray.add(rs.getString("spelutvecklare"));
                innerArray.add(rs.getString("plattform"));
                arr.add(innerArray);
            }
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
