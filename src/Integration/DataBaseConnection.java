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

    public ArrayList<ArrayList<String>> showStoresWithoutGivenProduct(String param) {
        try {
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            String query = "SELECT Butik.adress, Butik.ort, Produkt.spelSerie, SpelSaldo.antal FROM Produkt INNER JOIN (Butik INNER JOIN SpelSaldo ON Butik.butik_id = SpelSaldo.butik_id) ON Produkt.produkt_id = SpelSaldo.produkt_id WHERE SpelSaldo.antal = 0 AND Produkt.spelSerie = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, param);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(rs.getString("adress"));
                innerArray.add(rs.getString("ort"));
                innerArray.add(rs.getString("antal"));
                arr.add(innerArray);
            }
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
