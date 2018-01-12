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
                innerArray.add(rs.getString("pris"));
                innerArray.add(rs.getString("spelSerie"));
                innerArray.add(rs.getString("spelutvecklare"));
                innerArray.add(rs.getString("plattform"));
                arr.add(innerArray);
            }
            statement.close();
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> showStoresWithoutGivenProduct(int param) {
        try {
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            String query = "SELECT Butik.adress, Butik.ort, Produkt.spelSerie, SpelSaldo.antal FROM Produkt " +
                    "INNER JOIN (Butik INNER JOIN SpelSaldo ON Butik.butik_id = SpelSaldo.butik_id) ON " +
                    "Produkt.produkt_id = SpelSaldo.produkt_id WHERE SpelSaldo.antal = 0 AND produkt_id = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, param);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(rs.getString("adress"));
                innerArray.add(rs.getString("ort"));
                innerArray.add(rs.getString("antal"));
                arr.add(innerArray);
            }
            prepStatement.close();
            return arr;
        } catch (Exception ex) {
            System.out.println("You must enter an integer");
        }
        return null;
    }

    public String läggTillNyBevakning(int produktParam, int butikParam, String emailParam) {
        try {
            String query;
            PreparedStatement prepStatement;
            ResultSet rs;
            boolean instock = false;
            int saldoId = 0;

            while (!instock) {
                query = "SELECT butik_id, ort FROM Butik";
                prepStatement = conn.prepareStatement(query);
                rs = prepStatement.executeQuery();
                query = "SELECT bevakar_id FROM Bevakare WHERE emejladress = ? ";
                prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, emailParam);
                rs = prepStatement.executeQuery();
                if (!rs.next()) {
                    query = "INSERT INTO Bevakare (emejlAdress) VALUES (?)";
                    prepStatement = conn.prepareStatement(query);
                    prepStatement.setString(1, emailParam);
                    prepStatement.executeUpdate();
                }
                query = "SELECT saldo_id FROM SpelSaldo INNER JOIN Butik ON " +
                        "SpelSaldo.butik_id = Butik.butik_id WHERE SpelSaldo.produkt_id = ? AND Butik.butik_id = ?";
                prepStatement = conn.prepareStatement(query);
                prepStatement.setInt(1, produktParam);
                prepStatement.setInt(2, butikParam);
                rs = prepStatement.executeQuery();
                while (rs.next()) {
                    saldoId = rs.getInt("saldo_id");
                    instock = true;
                }
                if (!instock) {
                    return "-- Det valda spelet finns inte tillgängigt i vald butik. Prova annan butik! --";
                }
            }
            query = "SELECT bevakar_id FROM Bevakare WHERE emejlAdress = ?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, emailParam);
            rs = prepStatement.executeQuery();
            int bevakarId = 0;
            while (rs.next()) {
                bevakarId = rs.getInt("bevakar_id");
            }

            query = "INSERT INTO Bevakning (saldo_id, bevakar_id) VALUES (?, ?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, saldoId);
            prepStatement.setInt(2, bevakarId);
            prepStatement.executeUpdate();
            prepStatement.close();
            return "Hej " + emailParam + "! Din nya bevakar ID är " + bevakarId +
                    ", du bevakar produkt med id " + produktParam + " i butiken med id " + butikParam;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public ArrayList<ArrayList<String>> showButiker() {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Butik");
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            while (rs.next()) {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(rs.getString("butik_id"));
                innerArray.add(rs.getString("adress"));
                innerArray.add(rs.getString("ort"));
                arr.add(innerArray);
            }
            statement.close();
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> productsWithGenreOrPlattform(String plattformParam, String genreParam) {
        try {
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            if (plattformParam.equals("")) {
                String query = "SELECT DISTINCT Produkt.produkt_id, SpelTitel.speltitel, Plattform.namn, Genre.gerneTyp\n" +
                        "FROM Plattform " +
                        "INNER JOIN ((SpelTitel INNER JOIN " +
                        "(Genre INNER JOIN GenreTabell ON Genre.gerneTyp = GenreTabell.genreTypFN)" +
                        "ON SpelTitel.titel_id = GenreTabell.titel_idFN) INNER JOIN Produkt " +
                        "ON SpelTitel.titel_id = Produkt.titel_id) ON Plattform.Namn = Produkt.plattform\n" +
                        "WHERE gerneTyp = ?;";
                PreparedStatement prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, genreParam);
                ResultSet rs = prepStatement.executeQuery();
                while (rs.next()) {
                    ArrayList<String> innerArray = new ArrayList<>();
                    innerArray.add(rs.getString(1));
                    innerArray.add(rs.getString(2));
                    innerArray.add(rs.getString(3));
                    innerArray.add(rs.getString(4));
                    arr.add(innerArray);
                }
                return arr;
            } else if (genreParam.equals("")) {
                String query = "SELECT DISTINCT Produkt.produkt_id, SpelTitel.speltitel, Plattform.namn, Genre.gerneTyp\n" +
                        "FROM Plattform " +
                        "INNER JOIN ((SpelTitel INNER JOIN " +
                        "(Genre INNER JOIN GenreTabell ON Genre.gerneTyp = GenreTabell.genreTypFN) " +
                        "ON SpelTitel.titel_id = GenreTabell.titel_idFN) INNER JOIN Produkt " +
                        "ON SpelTitel.titel_id = Produkt.titel_id) ON Plattform.Namn = Produkt.plattform\n" +
                        "WHERE Plattform.namn = ?;";
                PreparedStatement prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, plattformParam);
                ResultSet rs = prepStatement.executeQuery();
                while (rs.next()) {
                    ArrayList<String> innerArray = new ArrayList<>();
                    innerArray.add(rs.getString(1));
                    innerArray.add(rs.getString(2));
                    innerArray.add(rs.getString(3));
                    innerArray.add(rs.getString(4));
                    arr.add(innerArray);
                }
                return arr;
            } else {
                String query = "SELECT DISTINCT Produkt.produkt_id, SpelTitel.speltitel, Plattform.namn, Genre.gerneTyp\n" +
                        "FROM Plattform " +
                        "INNER JOIN ((SpelTitel INNER JOIN " +
                        "(Genre INNER JOIN GenreTabell ON Genre.gerneTyp = GenreTabell.genreTypFN) " +
                        "ON SpelTitel.titel_id = GenreTabell.titel_idFN) INNER JOIN Produkt ON SpelTitel.titel_id = Produkt.titel_id) " +
                        "ON Plattform.Namn = Produkt.plattform\n" +
                        "WHERE gerneTyp = ? AND plattform = ?";
                PreparedStatement prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, genreParam);
                prepStatement.setString(2, plattformParam);
                ResultSet rs = prepStatement.executeQuery();
                while (rs.next()) {
                    ArrayList<String> innerArray = new ArrayList<>();
                    innerArray.add(rs.getString(1));
                    innerArray.add(rs.getString(2));
                    innerArray.add(rs.getString(3));
                    innerArray.add(rs.getString(4));
                    arr.add(innerArray);
                }

                return arr;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> getGenre() {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Genre");
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            while (rs.next()) {
                ArrayList<String> innerArray = new ArrayList<>();
                innerArray.add(rs.getString(1));
                arr.add(innerArray);
            }
            statement.close();
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
