package autobusIspit;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // Povezivanje sa bazom
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect = DriverManager.getConnection(
            "jdbc:mysql://localhost/autobusiIspit", // ime baze
            "root", // korisničko ime
            "" // lozinka
        );
    }

    // Metoda za čitanje svih putnika iz baze
    public ArrayList<Putnik> vratiPutnike() throws ClassNotFoundException, SQLException {
        ArrayList<Putnik> lista = new ArrayList<>();

        connect();
        statement = connect.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM putnik"); // naziv tabele u bazi

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            int godiste = resultSet.getInt("godiste");

            Putnik p = new Putnik(id, ime, prezime, godiste);
            lista.add(p);
        }
        close();
        return lista;
    }

    // Metoda za unos novog putnika
    public void unesiPutnika(Putnik p) throws SQLException, ClassNotFoundException {
        connect();
        preparedStatement = connect.prepareStatement(
            "INSERT INTO putnik (ime, prezime, godiste) VALUES (?, ?, ?)"
        );
        preparedStatement.setString(1, p.getIme());
        preparedStatement.setString(2, p.getPrezime());
        preparedStatement.setInt(3, p.getGodiste());
        preparedStatement.executeUpdate();
        close();
    }

    // Brisanje putnika po ID-u (ako želiš dodatnu funkcionalnost)
    public void obrisiPutnika(int id) throws SQLException, ClassNotFoundException {
        connect();
        preparedStatement = connect.prepareStatement("DELETE FROM putnik WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        close();
    }

    // Zatvaranje konekcije
    private void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
